package io.chrisdavenport.process

import cats.syntax.all._
import cats.effect._
import scalajs.js
import scalajs.js.|

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
        internal.childProcessMod.exec(commandS, method)
        None
      }
    }
    
    def exec(command: String, args: List[String]): F[String] = execSimple(command, args).map(_._1)
  }

}