package io.chrisdavenport.process

import cats.syntax.all._
import cats.effect._
import scalajs.js
import scalajs.js.|
import js.JSConverters._


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
        process.addListener_exit(internal.childProcessMod.exit, onExit)
        process.addListener_error(internal.childProcessMod.error, onError)
        Some(Async[F].delay(process.kill()).void)
      }
    }
    
    def exec(command: String, args: List[String]): F[String] = execSimple(command, args).map(_._1)
  }

}