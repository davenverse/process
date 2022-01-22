package io.chrisdavenport.process

import cats.syntax.all._
// import fs2.io.file.Path

case class Process(command: String, args: List[String])
// env: Option[Map[String, String]] = None, workingDirectory: Option[Path] = None, 
object Process {
  def fromString(s: String): Either[Throwable, Process] = {
    if (s.nonEmpty) s.split(" ").toList.toNel.toRight(new Throwable("Empty String is invalid process")).map(nel => 
      Process(nel.head, nel.tail)
    )
    else new Throwable("Empty String is invalid process").asLeft
  }
}