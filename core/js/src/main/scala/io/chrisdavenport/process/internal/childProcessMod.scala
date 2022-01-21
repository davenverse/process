package io.chrisdavenport.process.internal

import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}

// @JSImport("child_process", JSImport.Namespace)
object childProcessMod {
  @JSImport("child_process", JSImport.Namespace)
  @js.native
  val ^ : js.Any = js.native

  @js.native
  trait ExecException extends js.Any {

    var message: java.lang.String = js.native
    
    var name: java.lang.String = js.native
    
    var stack: js.UndefOr[java.lang.String] = js.native
    
    var cmd: js.UndefOr[String] = js.native
    
    var code: js.UndefOr[Double] = js.native
    
    var killed: js.UndefOr[Boolean] = js.native
    
    // var signal: js.UndefOr[Signals] = js.native
  }

  @scala.inline
  def exec(
    command: java.lang.String,
    callback: js.Function3[
      /* error */ ExecException, 
      /* stdout */ java.lang.String, 
      /* stderr */ java.lang.String, 
      Unit
    ]
  ): Any = (^.asInstanceOf[js.Dynamic].applyDynamic("exec")(command.asInstanceOf[js.Any], callback.asInstanceOf[js.Any]))//.asInstanceOf[ChildProcess]
}