
import cats.effect._
import io.chrisdavenport.process._
import io.chrisdavenport.process.syntax.all._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val cp = ChildProcess.impl[IO]
    cp.exec(process"tput lines").flatTap(s => IO.println(s"Stdout: $s")) >>
    cp.spawn(process"tput cols").flatTap{ rp => 
      
      rp.stdout.through(fs2.text.utf8.decode).compile.string.flatTap(stdout => IO.println(s"Stdout: $stdout")) >>
      rp.getExitCode.flatTap(code => IO.println(s"Code: $code"))
    } >>
    IO.unit.as(ExitCode.Success)
  }

}