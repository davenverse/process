package io.chrisdavenport.process

import cats.effect._
import fs2._
import fs2.io.file.Path

case class Process(command: String, args: List[String])
// env: Option[Map[String, String]] = None, workingDirectory: Option[Path] = None, 

trait RunningProcess[F[_]]{
  def getExitCode: F[Int] // Waits for program to terminate and returns the code

  def writeToStdIn(s: Stream[F, Byte]): F[Unit]
  def terminate: F[Unit]

  def stdout: Stream[F, Byte]
  def stderr: Stream[F, Byte]
}

trait ChildProcess[F[_]]{
  def exec(command: String, args: List[String] = List.empty): F[String]
  def execCode(command: String, args: List[String] = List.empty): F[Int]

  def spawn(process: Process): F[RunningProcess[F]]
}

object ChildProcess extends ChildProcessCompanionPlatform {

  def impl[F[_]: Async]: ChildProcess[F] = new ChildProcessImpl[F]

}