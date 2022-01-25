package io.chrisdavenport.process

import scala.language.experimental.macros
import org.typelevel.literally.Literally

object LiteralProcess {
  object process extends Literally[Process] {
    def validate(c: Context)(s: String) = {
      import c.universe._
      Process.fromString(s) match {
        case Right(_) => Right(c.Expr(q"_root_.io.chrisdavenport.process.Process.fromString($s).fold(throw _ , identity)"))
        case Left(_)    => Left("invalid process string")
      }
    }
    def make(c: Context)(args: c.Expr[Any]*): c.Expr[Process] = apply(c)(args: _*)
  }

  trait LiteralProcessSyntax {
    final implicit class IpLiteralSyntax(private val sc: StringContext) {
      def process(args: Any*): Process = macro LiteralProcess.process.make
    }
  }

}