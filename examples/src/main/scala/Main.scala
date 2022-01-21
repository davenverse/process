
import cats.effect._
import io.chrisdavenport.process._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val cp = ChildProcess.impl[IO]
    cp.execCode("tput", "cols" :: Nil).flatTap(s => IO.println(s"${s}")) >>
    IO.unit.as(ExitCode.Success)
  }

}