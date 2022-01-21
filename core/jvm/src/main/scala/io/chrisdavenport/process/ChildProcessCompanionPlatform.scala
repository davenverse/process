package io.chrisdavenport.process

import cats.effect._



trait ChildProcessCompanionPlatform {

  private[process] class ChildProcessImpl[F[_]: Async] extends ChildProcess[F]{
    def exec(command: String, args: List[String]): F[String] = Sync[F].delay{
      import sys.process._
      (command :: args).!!
    }
    def execCode(command: String, args: List[String]): F[Int] = Sync[F].delay{
      import sys.process._
      // TODO Get rid of this as it sends stdout to the process console
      (command :: args).! 
    }
  }
  
}
