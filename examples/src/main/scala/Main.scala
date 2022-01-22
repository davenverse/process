
import cats.effect._
import io.chrisdavenport.process._

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] = {
    val cp = ChildProcess.impl[IO]
    cp.exec("tput", "cols":: Nil).flatTap(s => IO.println(s"Stdout: $s")) >>
    cp.spawn(Process("tput", "cols" :: Nil)).flatTap{ rp => 
      
      rp.stdout.through(fs2.text.utf8.decode).compile.string.flatTap(stdout => IO.println(s"Stdout: $stdout")) >>
      rp.getExitCode.flatTap(code => IO.println(s"Code: $code"))
    } >>
    // cp.execCode("tput", "cols" :: Nil).flatTap(s => IO.println(s"${s}")) >>
    IO.unit.as(ExitCode.Success)
  }

}