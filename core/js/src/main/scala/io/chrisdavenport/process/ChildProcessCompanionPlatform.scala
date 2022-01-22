package io.chrisdavenport.process

import cats.syntax.all._
import cats.effect._
import cats.effect.syntax.all._
import scalajs.js
import scalajs.js.|
import js.JSConverters._
import fs2.Stream
import io.chrisdavenport.process.internal.childProcessMod
import com.comcast.ip4s.Literals
import scala.scalajs.js.typedarray.{ArrayBuffer, TypedArrayBuffer, Uint8Array}


trait ChildProcessCompanionPlatform {

  private[process] class ChildProcessImpl[F[_]: Async] extends ChildProcess[F]{
    def execSimple(command: String, args: List[String]): F[(String, String)] = Async[F].async{(cb: Either[Throwable, (String, String)] => Unit) => 
      Async[F].delay{
        val commandS = command ++ { if (args.nonEmpty) " " else "" } ++ args.mkString(" ")
        def method(error: internal.childProcessMod.ExecException, stdout: String, stderr: String): Unit = {
          if (error != null) {
            cb(Left(new Throwable(s"Failed Process Execution, ${error.cmd.toOption} ${error.message}")))
          } else cb(Right((stdout, stderr)))
        }
        val process = internal.childProcessMod.exec(commandS, method)
        Some(Async[F].delay(process.kill()).void)
      }
    }

    def execCode(command: String, args: List[String]): F[Int] = Async[F].async{(cb: Either[Throwable, Int] => Unit) => 
      Async[F].delay{
        def onExit(int: Double, signal: String): Unit = 
          cb(Either.right(int.toInt))
        def onError(error: scalajs.js.Error): Unit = 
          cb(Either.left(new Throwable(s"${error.name}: ${error.message}")))
        val process = internal.childProcessMod.spawn(command, args.toJSArray)
        process.addListener_exit(internal.childProcessMod.strings.exit, onExit)
        process.addListener_error(internal.childProcessMod.strings.error, onError)
        Some(Async[F].delay(process.kill()).void)
      }
    }
    
    def exec(command: String, args: List[String]): F[String] = execSimple(command, args).map(_._1)

    def spawn(process: Process): F[RunningProcess[F]] = Async[F].delay{
      // Initial State
      val iprocess = internal.childProcessMod.spawn(process.command, process.args.toJSArray)

      // Process Management at process level
      val exitCodeD = structures.UnsafeDeferred[F, Int]
      val errorD = structures.UnsafeDeferred[F, Throwable]

      def onExit(int: Double, signal: String): Unit = {
        exitCodeD.complete(int.toInt)
        // TODO cancel from starting string processign
      }
      def onError(error: scalajs.js.Error): Unit = errorD.complete(new Throwable(s"${error.name}: ${error.message}"))
      iprocess.addListener_exit(internal.childProcessMod.strings.exit, onExit)
      iprocess.addListener_error(internal.childProcessMod.strings.error, onError)


      // stdout
      def convert(buffer: js.typedarray.Uint8Array): fs2.Chunk[Byte] = {
        fs2.Chunk.byteBuffer(
          TypedArrayBuffer
            .wrap(
              buffer.buffer.asInstanceOf[ArrayBuffer],
              buffer.byteOffset.toInt,
              buffer.byteLength.toInt
            )
        )
      }
      val stdout = iprocess.stdout
      val stdoutBuffer = structures.UnsafeByteQueue.impl[F]
      val onReadStdout = new Function0[Unit]{
        def apply(): Unit = {
          val data = stdout.read()
          if (data == null) stdoutBuffer.close()
          else stdoutBuffer.offer(convert(data))
        }
      }
      

      stdout.addListener_readable(childProcessMod.strings.readable, onReadStdout)
      
      val stderr = iprocess.stderr
      val stderrBuffer = structures.UnsafeByteQueue.impl[F]
      val onReadStderr = new Function0[Unit]{
        def apply(): Unit = {
          val data = stdout.read()
          if (data == null) stdoutBuffer.close()
          else stdoutBuffer.offer(convert(data))
        }
      }

      stderr.addListener_readable(childProcessMod.strings.readable, onReadStderr)

      new RunningProcess[F] {
        def getExitCode: F[Int] = Concurrent[F].race(errorD.get, exitCodeD.get).rethrow

        def writeToStdIn(s: Stream[F, Byte]): F[Unit] = 
          s.through(fs2.io.writeWritable(iprocess.stdin.pure[F])).compile.drain
        
        def terminate: F[Unit] = Async[F].delay(iprocess.kill()).void
        
        def stdout: fs2.Stream[F,Byte] = stdoutBuffer.reads //Stream.resource(fs2.io.suspendReadableAndRead(false, false)(iprocess.stdout)).flatMap(_._2)
        
        def stderr: fs2.Stream[F,Byte] = stderrBuffer.reads // Stream.resource(fs2.io.suspendReadableAndRead(false, false)(iprocess.stderr)).flatMap(_._2)
        
      }
    }
  }

}