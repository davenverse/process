package io.chrisdavenport.process

import org.typelevel.literally.Literally

object LiteralProcess {
  object process extends Literally[Process]{
    def validate(s: String)(using Quotes) =
      Process.fromString(s) match
        case Right(_) => Right('{ _root_.io.chrisdavenport.process.Process.fromString(${ Expr(s) }).fold(throw _ , identity)})
        case Left(_)    => Left("invalid process string")
  }

  trait LiteralProcessSyntax {
    extension (inline ctx: StringContext)
      inline def process(inline args: Any*): Process =
        ${ LiteralProcess.process('ctx, 'args) }
  }
}