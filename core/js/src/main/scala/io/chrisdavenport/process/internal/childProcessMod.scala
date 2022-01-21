package io.chrisdavenport.process.internal

import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}
import fs2.io.Readable
import fs2.io.Writable

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
    
    var signal: js.UndefOr[String] = js.native
  }

  @js.native
  sealed trait close extends js.Any
  @scala.inline
  def close: close = "close".asInstanceOf[close]

  @js.native
  sealed trait disconnect extends js.Any
  @scala.inline
  def disconnect: disconnect = "disconnect".asInstanceOf[disconnect]

  @js.native
  sealed trait error extends js.Any
  @scala.inline
  def error: error = "error".asInstanceOf[error]

  @js.native
  sealed trait exit extends js.Any
  @scala.inline
  def exit: exit = "exit".asInstanceOf[exit]

  @js.native
  sealed trait spawn extends js.Any
  @scala.inline
  def spawn: spawn = "spawn".asInstanceOf[spawn]
  

  @js.native
  trait ChildProcess extends js.Any {
    
    /**
      * events.EventEmitter
      * 1. close
      * 2. disconnect
      * 3. error
      * 4. exit
      * 5. message
      * 6. spawn
      */
    def addListener(event: String, listener: js.Function1[/* repeated */ js.Any, Unit]): this.type = js.native
    @JSName("addListener")
    def addListener_close(event: close, listener: js.Function2[/* code */ Double, /* signal */ String, Unit]): this.type = js.native
    @JSName("addListener")
    def addListener_disconnect(event: disconnect, listener: js.Function0[Unit]): this.type = js.native
    @JSName("addListener")
    def addListener_error(event: error, listener: js.Function1[/* err */ js.Error, Unit]): this.type = js.native
    @JSName("addListener")
    def addListener_exit(event: exit, listener: js.Function2[/* code */ Double, /* signal */ String, Unit]): this.type = js.native
    // @JSName("addListener")
    // def addListener_message(
    //   event: message,
    //   listener: js.Function2[/* message */ Serializable, /* sendHandle */ SendHandle, Unit]
    // ): this.type = js.native
    @JSName("addListener")
    def addListener_spawn(event: spawn, listener: js.Function0[Unit]): this.type = js.native
    
    // val channel: js.UndefOr[Pipe | Null] = js.native
    
    val connected: Boolean = js.native
    
    def disconnect(): Unit = js.native
    
    // def emit(event: String, args: js.Any*): Boolean = js.native
    // def emit(event: js.Symbol, args: js.Any*): Boolean = js.native
    // @JSName("emit")
    // def emit_close(event: close): Boolean = js.native
    // @JSName("emit")
    // def emit_close(event: close, code: Double): Boolean = js.native
    // @JSName("emit")
    // def emit_close(event: close, code: Double, signal: Signals): Boolean = js.native
    // @JSName("emit")
    // def emit_close(event: close, code: Null, signal: Signals): Boolean = js.native
    // @JSName("emit")
    // def emit_disconnect(event: disconnect): Boolean = js.native
    // @JSName("emit")
    // def emit_error(event: error, err: js.Error): Boolean = js.native
    // @JSName("emit")
    // def emit_exit(event: exit): Boolean = js.native
    // @JSName("emit")
    // def emit_exit(event: exit, code: Double): Boolean = js.native
    // @JSName("emit")
    // def emit_exit(event: exit, code: Double, signal: Signals): Boolean = js.native
    // @JSName("emit")
    // def emit_exit(event: exit, code: Null, signal: Signals): Boolean = js.native
    // @JSName("emit")
    // def emit_message(event: message, message: Serializable, sendHandle: SendHandle): Boolean = js.native
    // @JSName("emit")
    // def emit_spawn(event: fs2.internal.jsdeps.node.nodeStrings.spawn, listener: js.Function0[Unit]): Boolean = js.native
    
    // Double | Null
    val exitCode: Double = js.native
    
    def kill(): Boolean = js.native
    // def kill(signal: Signals): Boolean = js.native
    // def kill(signal: Double): Boolean = js.native
    
    val killed: Boolean = js.native
    
    // def on(event: String, listener: js.Function1[/* repeated */ js.Any, Unit]): this.type = js.native
    // @JSName("on")
    // def on_close(event: close, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("on")
    // def on_disconnect(event: disconnect, listener: js.Function0[Unit]): this.type = js.native
    // @JSName("on")
    // def on_error(event: error, listener: js.Function1[/* err */ js.Error, Unit]): this.type = js.native
    // @JSName("on")
    // def on_exit(event: exit, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("on")
    // def on_message(
    //   event: message,
    //   listener: js.Function2[/* message */ Serializable, /* sendHandle */ SendHandle, Unit]
    // ): this.type = js.native
    // @JSName("on")
    // def on_spawn(event: fs2.internal.jsdeps.node.nodeStrings.spawn, listener: js.Function0[Unit]): this.type = js.native
    
    // def once(event: String, listener: js.Function1[/* repeated */ js.Any, Unit]): this.type = js.native
    // @JSName("once")
    // def once_close(event: close, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("once")
    // def once_disconnect(event: disconnect, listener: js.Function0[Unit]): this.type = js.native
    // @JSName("once")
    // def once_error(event: error, listener: js.Function1[/* err */ js.Error, Unit]): this.type = js.native
    // @JSName("once")
    // def once_exit(event: exit, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("once")
    // def once_message(
    //   event: message,
    //   listener: js.Function2[/* message */ Serializable, /* sendHandle */ SendHandle, Unit]
    // ): this.type = js.native
    // @JSName("once")
    // def once_spawn(event: fs2.internal.jsdeps.node.nodeStrings.spawn, listener: js.Function0[Unit]): this.type = js.native
    
    val pid: js.UndefOr[Double] = js.native
    
    // def prependListener(event: String, listener: js.Function1[/* repeated */ js.Any, Unit]): this.type = js.native
    // @JSName("prependListener")
    // def prependListener_close(event: close, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("prependListener")
    // def prependListener_disconnect(event: disconnect, listener: js.Function0[Unit]): this.type = js.native
    // @JSName("prependListener")
    // def prependListener_error(event: error, listener: js.Function1[/* err */ js.Error, Unit]): this.type = js.native
    // @JSName("prependListener")
    // def prependListener_exit(event: exit, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("prependListener")
    // def prependListener_message(
    //   event: message,
    //   listener: js.Function2[/* message */ Serializable, /* sendHandle */ SendHandle, Unit]
    // ): this.type = js.native
    // @JSName("prependListener")
    // def prependListener_spawn(event: fs2.internal.jsdeps.node.nodeStrings.spawn, listener: js.Function0[Unit]): this.type = js.native
    
    // def prependOnceListener(event: String, listener: js.Function1[/* repeated */ js.Any, Unit]): this.type = js.native
    // @JSName("prependOnceListener")
    // def prependOnceListener_close(event: close, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("prependOnceListener")
    // def prependOnceListener_disconnect(event: disconnect, listener: js.Function0[Unit]): this.type = js.native
    // @JSName("prependOnceListener")
    // def prependOnceListener_error(event: error, listener: js.Function1[/* err */ js.Error, Unit]): this.type = js.native
    // @JSName("prependOnceListener")
    // def prependOnceListener_exit(event: exit, listener: js.Function2[/* code */ Double | Null, /* signal */ Signals | Null, Unit]): this.type = js.native
    // @JSName("prependOnceListener")
    // def prependOnceListener_message(
    //   event: message,
    //   listener: js.Function2[/* message */ Serializable, /* sendHandle */ SendHandle, Unit]
    // ): this.type = js.native
    // @JSName("prependOnceListener")
    // def prependOnceListener_spawn(event: fs2.internal.jsdeps.node.nodeStrings.spawn, listener: js.Function0[Unit]): this.type = js.native
    
    // def ref(): Unit = js.native
    
    // def send(message: Serializable): Boolean = js.native
    // def send(message: Serializable, callback: js.Function1[/* error */ js.Error | Null, Unit]): Boolean = js.native
    // def send(message: Serializable, sendHandle: SendHandle): Boolean = js.native
    // def send(
    //   message: Serializable,
    //   sendHandle: SendHandle,
    //   callback: js.Function1[/* error */ js.Error | Null, Unit]
    // ): Boolean = js.native
    // def send(message: Serializable, sendHandle: SendHandle, options: MessageOptions): Boolean = js.native
    // def send(
    //   message: Serializable,
    //   sendHandle: SendHandle,
    //   options: MessageOptions,
    //   callback: js.Function1[/* error */ js.Error | Null, Unit]
    // ): Boolean = js.native
    // def send(
    //   message: Serializable,
    //   sendHandle: SendHandle,
    //   options: Unit,
    //   callback: js.Function1[/* error */ js.Error | Null, Unit]
    // ): Boolean = js.native
    // def send(message: Serializable, sendHandle: Unit, callback: js.Function1[/* error */ js.Error | Null, Unit]): Boolean = js.native
    // def send(message: Serializable, sendHandle: Unit, options: MessageOptions): Boolean = js.native
    // def send(
    //   message: Serializable,
    //   sendHandle: Unit,
    //   options: MessageOptions,
    //   callback: js.Function1[/* error */ js.Error | Null, Unit]
    // ): Boolean = js.native
    // def send(
    //   message: Serializable,
    //   sendHandle: Unit,
    //   options: Unit,
    //   callback: js.Function1[/* error */ js.Error | Null, Unit]
    // ): Boolean = js.native
    
    val signalCode: String = js.native
    
    val spawnargs: js.Array[String] = js.native
    
    val spawnfile: String = js.native
    
    var stderr: Readable  = js.native
    
    var stdin: Writable  = js.native
    
    // val stdio: js.Tuple5[
    //   Writable, 
    //   (// stdin
    // Readable), 
    //   (// stdout
    // Readable), 
    //   js.UndefOr[(// stderr
    // Readable) | Writable], 
    //   js.UndefOr[(// extra
    // Readable) | Writable]
    // ] = js.native
    
    var stdout: Readable = js.native
    
    def unref(): Unit = js.native
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
  ): Any = (^.asInstanceOf[js.Dynamic].applyDynamic("exec")(command.asInstanceOf[js.Any], callback.asInstanceOf[js.Any])).asInstanceOf[ChildProcess]

  @scala.inline
  def spawn(command: java.lang.String, args: js.Array[java.lang.String]): ChildProcess = (^.asInstanceOf[js.Dynamic].applyDynamic("spawn")(command.asInstanceOf[js.Any], args.asInstanceOf[js.Any])).asInstanceOf[ChildProcess]
}