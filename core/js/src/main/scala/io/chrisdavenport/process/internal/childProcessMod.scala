package io.chrisdavenport.process.internal

import scala.scalajs.js
import scala.scalajs.js.`|`
import scala.scalajs.js.annotation.{JSGlobalScope, JSGlobal, JSImport, JSName, JSBracketAccess}
// import fs2.io.Readable
import fs2.io.Writable

object childProcessMod {
  @JSImport("child_process", JSImport.Namespace)
  @js.native
  val ^ : js.Any = js.native

  object strings {
    @js.native
    sealed trait readable extends js.Any
    @scala.inline
    def readable: readable = "readable".asInstanceOf[readable]

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

  }
  import strings._



  @JSImport("stream", "Readable")
  @js.native
  class Readable ()
    extends js.Any {
    
    def _read(size: Double): Unit = js.native
    
    /**
      * Event emitter
      * The defined events on documents including:
      * 1. close
      * 2. data
      * 3. end
      * 4. error
      * 5. pause
      * 6. readable
      * 7. resume
      */
    // @JSName("addListener")
    // def addListener_close(event: close, listener: js.Function0[Unit]): this.type = js.native
    // @JSName("addListener")
    // def addListener_data(event: data, listener: js.Function1[/* chunk */ js.Any, Unit]): this.type = js.native
    // @JSName("addListener")
    // def addListener_end(event: end, listener: js.Function0[Unit]): this.type = js.native
    // @JSName("addListener")
    // def addListener_error(event: error, listener: js.Function1[/* err */ js.Error, Unit]): this.type = js.native
    // @JSName("addListener")
    // def addListener_pause(event: pause, listener: js.Function0[Unit]): this.type = js.native
    @JSName("addListener")
    def addListener_readable(event: readable, listener: js.Function0[Unit]): this.type = js.native
    // @JSName("addListener")
    // def addListener_resume(event: resume, listener: js.Function0[Unit]): this.type = js.native
    
    def destroy(): Unit = js.native
    def destroy(error: js.Error): Unit = js.native
    
    var destroyed: Boolean = js.native

    def read(): js.typedarray.Uint8Array = js.native
    
    
    val readableEnded: Boolean = js.native
    
    val readableFlowing: Boolean | Null = js.native
    
    val readableHighWaterMark: Double = js.native
    
    val readableLength: Double = js.native
  }

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
    
    // Double | Null
    val exitCode: Double = js.native
    
    def kill(): Boolean = js.native
    
    val killed: Boolean = js.native
    
    val pid: js.UndefOr[Double] = js.native
    
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
  ): ChildProcess = (^.asInstanceOf[js.Dynamic].applyDynamic("exec")(command.asInstanceOf[js.Any], callback.asInstanceOf[js.Any])).asInstanceOf[ChildProcess]

  @scala.inline
  def spawn(command: java.lang.String, args: js.Array[java.lang.String]): ChildProcess = (^.asInstanceOf[js.Dynamic].applyDynamic("spawn")(command.asInstanceOf[js.Any], args.asInstanceOf[js.Any])).asInstanceOf[ChildProcess]
}