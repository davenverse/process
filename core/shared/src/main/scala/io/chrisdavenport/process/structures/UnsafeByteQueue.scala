package io.chrisdavenport.process.structures

import cats.effect._
import fs2.Chunk
import cats.syntax.all._

trait UnsafeByteQueue[F[_]]{
  def close(): Unit
  def offer(chunk: Chunk[Byte]): Unit
  def get: F[Option[Chunk[Byte]]]
  def reads = fs2.Stream.repeatEval(get).unNoneTerminate.flatMap(fs2.Stream.chunk)
}

object UnsafeByteQueue {

  def impl[F[_]: Async]: UnsafeByteQueue[F] = new ByteQueueImpl[F](UnsafeRef.of(Right(Chunk.empty, false)))

  class ByteQueueImpl[F[_]: Async](ref: UnsafeRef[Either[UnsafeDeferred[F, Unit], (Chunk[Byte], Boolean)]] ) extends UnsafeByteQueue[F]{
    def close(): Unit = ref.modify{
      case Left(defer) => ((Chunk.empty[Byte], true).asRight, {_: Unit => defer.complete(())})
      case Right((chunk, _)) => ((chunk, true).asRight, {_: Unit => ()})
    }()

    def offer(chunk: Chunk[Byte]): Unit = ref.modify{
      case Left(defer) => (Right((chunk, false)), {_: Unit => defer.complete(())})
      case Right((init, false)) => (Right(init ++ chunk, false), {_: Unit => ()})
      case Right(init) => init.asRight -> {_: Unit => ()}
    }()
    
    def get: F[Option[Chunk[Byte]]] = Sync[F].delay(ref.modify{
      case Left(e) => (Left(e), new Throwable("Only 1 Get Allowed at a time").raiseError[F, Option[Chunk[Byte]]])
      case Right((chunk, true)) if chunk.isEmpty => (chunk, true).asRight -> Option.empty[Chunk[Byte]].pure[F]
      case Right((chunk, true)) => (Chunk.empty, true).asRight -> Option(chunk).pure[F]
      case Right((chunk, false)) if chunk.isEmpty =>  
        val u = UnsafeDeferred[F, Unit]
        (Left(u), u.get.flatMap(_ => get))
      case Right((chunk, false)) => ((Chunk.empty[Byte], false).asRight, chunk.some.pure[F])
    }).flatten
    
  }
}