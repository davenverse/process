package io.chrisdavenport.process.structures

import scala.annotation.tailrec
import scala.collection.immutable.LongMap
import java.util.concurrent.atomic.AtomicReference

import cats.effect._
import cats.syntax.all._

trait UnsafeDeferred[F[_], A] extends cats.effect.kernel.DeferredSource[F, A]{
  def complete(a: A): Boolean
}
object UnsafeDeferred {
  sealed abstract private class State[A]
  private object State {
    final case class Set[A](a: A) extends State[A]
    final case class Unset[A](readers: LongMap[A => Unit], nextId: Long) extends State[A]

    val initialId = 1L
    val dummyId = 0L
  }
  def apply[F[_]: Async, A]: UnsafeDeferred[F, A] = new AsyncDeferred[F, A]

  final class AsyncDeferred[F[_], A](implicit F: Async[F]) extends UnsafeDeferred[F, A] {
    // shared mutable state
    private[this] val ref = new AtomicReference[State[A]](
      State.Unset(LongMap.empty, State.initialId)
    )

    def get: F[A] = {
      // side-effectful
      def addReader(awakeReader: A => Unit): Long = {
        @tailrec
        def loop(): Long =
          ref.get match {
            case State.Set(a) =>
              awakeReader(a)
              State.dummyId // never used
            case s @ State.Unset(readers, nextId) =>
              val updated = State.Unset(
                readers + (nextId -> awakeReader),
                nextId + 1
              )

              if (!ref.compareAndSet(s, updated)) loop()
              else nextId
          }

        loop()
      }

      // side-effectful
      def deleteReader(id: Long): Unit = {
        @tailrec
        def loop(): Unit =
          ref.get match {
            case State.Set(_) => ()
            case s @ State.Unset(readers, _) =>
              val updated = s.copy(readers = readers - id)
              if (!ref.compareAndSet(s, updated)) loop()
              else ()
          }

        loop()
      }

      F.defer {
        ref.get match {
          case State.Set(a) =>
            F.pure(a)
          case State.Unset(_, _) =>
            F.async[A] { cb =>
              val resume = (a: A) => cb(Right(a))
              F.delay(addReader(awakeReader = resume)).map { id =>
                // if canceled
                F.delay(deleteReader(id)).some
              }
            }
        }
      }
    }

    def tryGet: F[Option[A]] =
      F.delay {
        ref.get match {
          case State.Set(a) => Some(a)
          case State.Unset(_, _) => None
        }
      }

    def complete(a: A): Boolean = {
      def notifyReaders(readers: LongMap[A => Unit]): Unit = {
        // LongMap iterators return values in unsigned key order,
        // which corresponds to the arrival order of readers since
        // insertion is governed by a monotonically increasing id
        val cursor = readers.valuesIterator

        while (cursor.hasNext) {
          val next = cursor.next()
          next(a)
        }
      }

      // side-effectful (even though it returns F[Unit])
      @tailrec
      def loop(): Boolean =
        ref.get match {
          case State.Set(_) =>
            false
          case s @ State.Unset(readers, _) =>
            val updated = State.Set(a)
            if (!ref.compareAndSet(s, updated)) loop()
            else {
              if (readers.isEmpty) () else notifyReaders(readers)
              true
            }
        }

      loop()
    }
  }

}