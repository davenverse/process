package io.chrisdavenport.process.structures

import cats.effect._
import cats.syntax.all._
import java.util.concurrent.atomic.AtomicReference
import scala.annotation.tailrec


private[process] trait UnsafeRef[A]{
  def get: A
  def set(a: A): Unit 
  def getAndSet(a: A): A 
  def getAndUpdate(f: A => A): A
  def tryUpdate(f: A => A): Boolean
  def tryModify[B](f: A => (A, B)): Option[B]
  def update(f: A => A): Unit 
  def updateAndGet(f: A => A): A
  def modify[B](f: A => (A, B)): B 
}

private[process] object UnsafeRef {

  // Do whatever you want, but make sure its wrapped in a sync block
  def of[A](a: A): UnsafeRef[A] = new SyncRef[A](new AtomicReference[A](a)) 

  final private class SyncRef[A](private[this] val ar: AtomicReference[A]) extends UnsafeRef[A]{
    def get: A = ar.get

    def set(a: A): Unit = ar.set(a)

    def getAndSet(a: A): A = ar.getAndSet(a)

    def getAndUpdate(f: A => A): A = {
      @tailrec
      def spin: A = {
        val a = ar.get
        val u = f(a)
        if (!ar.compareAndSet(a, u)) spin
        else a
      }
      spin
    }


    def tryUpdate(f: A => A): Boolean =
      tryModify(a => (f(a), ())).isDefined

    def tryModify[B](f: A => (A, B)): Option[B] = {
        val c = ar.get
        val (u, b) = f(c)
        if (ar.compareAndSet(c, u)) Some(b)
        else None
    }

    def update(f: A => A): Unit = {
      @tailrec
      def spin(): Unit = {
        val a = ar.get
        val u = f(a)
        if (!ar.compareAndSet(a, u)) spin()
      }
      spin()
    }

    override def updateAndGet(f: A => A): A = {
      @tailrec
      def spin: A = {
        val a = ar.get
        val u = f(a)
        if (!ar.compareAndSet(a, u)) spin
        else u
      }
      spin
    }

    def modify[B](f: A => (A, B)): B = {
      @tailrec
      def spin: B = {
        val c = ar.get
        val (u, b) = f(c)
        if (!ar.compareAndSet(c, u)) spin
        else b
      }
      spin
    }
  }
}