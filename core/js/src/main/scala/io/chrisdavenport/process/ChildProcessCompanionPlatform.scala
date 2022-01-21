package io.chrisdavenport.process

import cats.effect._
import scalajs.js
import scalajs.js.|

trait ChildProcessCompanionPlatform {

  private[process] class ChildProcessImpl[F[_]: Async] extends ChildProcess[F]{
    def exec(command: String, args: List[String]): F[String] = Async[F].async(cb => 
      Async[F].delay{
        val commandS = command ++ " " ++ args.mkString(" ")
        def method(error: internal.childProcessMod.ExecException, stdout: String, stderr: String): Unit = {
          if (error != null) {
            new Throwable(s"Failed Process Execution, ${error.cmd.toOption} ${error.message}")
          } else cb(Right(stdout))
        }
        internal.childProcessMod.exec(commandS, method)
        None
      }
    )
  }

}