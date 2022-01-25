package io.chrisdavenport.process

import cats.effect._
import fs2._

trait ChildProcess[F[_]]{
  def exec(process: Process): F[String]
  def execCode(process: Process): F[Int]

  def spawn(process: Process): F[RunningProcess[F]]
}

object ChildProcess extends ChildProcessCompanionPlatform {

  def impl[F[_]: Async]: ChildProcess[F] = new ChildProcessImpl[F]

}