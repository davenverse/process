package io.chrisdavenport.process

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
    final implicit class IpLiteralSyntax(val sc: StringContext) extends AnyVal {
      def process(args: Any*): Process = macro Literals.process.make
    }
  }

}