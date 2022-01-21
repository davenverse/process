package io.chrisdavenport.process

import cats.effect._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val cp = ChildProcess.impl[IO]
    cp.exec("fail", Nil).flatTap(s => IO.println(s"${s} ${s.length}")) >>
    IO.unit.as(ExitCode.Success)
  }

}