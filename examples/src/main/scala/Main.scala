
import cats.effect._
import io.chrisdavenport.process._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val cp = ChildProcess.impl[IO]
    cp.exec("exit", Nil).flatTap(s => IO.println(s"${s} ${s.length}")) >>
    IO.unit.as(ExitCode.Success)
  }

}