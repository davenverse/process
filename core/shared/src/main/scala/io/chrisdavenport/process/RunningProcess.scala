package io.chrisdavenport.process

import fs2.Stream

trait RunningProcess[F[_]]{
  def getExitCode: F[Int] // Waits for program to terminate and returns the code

  def writeToStdIn(s: Stream[F, Byte]): F[Unit]
  def terminate: F[Unit]

  def stdout: Stream[F, Byte]
  def stderr: Stream[F, Byte]
}
