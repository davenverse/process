package io.chrisdavenport.process

import cats.effect._

trait ChildProcess[F[_]]{
  def exec(command: String, args: List[String] = List.empty): F[String]
  def execCode(command: String, args: List[String] = List.empty): F[Int]
}

object ChildProcess extends ChildProcessCompanionPlatform {

  def impl[F[_]: Async]: ChildProcess[F] = new ChildProcessImpl[F]

}