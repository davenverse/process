package io.chrisdavenport.process

import cats.effect._
import cats.syntax.all._

trait ChildProcessCompanionPlatform {

  private[process] class ChildProcessImpl[F[_]: Async] extends ChildProcess[F]{
    def exec(process: Process): F[String] = 
      spawn(process)
        .flatMap(_.stdout.through(fs2.text.utf8.decode).compile.string)

    def execCode(process: Process): F[Int] = 
      spawn(process).flatMap(_.getExitCode)

    val readBufferSize = 4096

    def spawn(process: Process): F[RunningProcess[F]] = Async[F].interruptible{
      import JDKCollectionConvertersCompat.Converters._
      val p = new java.lang.ProcessBuilder((process.command :: process.args).asJava).start()//.directory(new java.io.File(wd)).start()
      val done = Async[F].fromCompletableFuture(Sync[F].delay(p.onExit()))

      new RunningProcess[F] {
        def getExitCode: F[Int] = done.map(_ => p.exitValue())
        def writeToStdIn(s: fs2.Stream[F,Byte]): F[Unit] =  s
          .through(fs2.io.writeOutputStream[F](Sync[F].interruptible(p.getOutputStream())))
          .compile
          .drain
        
        def terminate: F[Unit] = Sync[F].interruptible(p.destroy())
        
        def stdout: fs2.Stream[F,Byte] = fs2
          .io
          .readInputStream[F](Sync[F].interruptible(p.getInputStream()), chunkSize = readBufferSize)
        
        def stderr: fs2.Stream[F,Byte] = fs2
          .io
          .readInputStream[F](Sync[F].blocking(p.getErrorStream()), chunkSize = readBufferSize)
          // Avoids broken pipe - we cut off when the program ends.
          // Users can decide what to do with the error logs using the exitCode value
          .interruptWhen(done.void.attempt)
      }
    }
  }
  
}
