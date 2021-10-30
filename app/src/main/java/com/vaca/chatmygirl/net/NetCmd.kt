package com.vaca.chatmygirl.net

import android.util.Log
import com.vaca.chatmygirl.BuildConfig
import io.socket.client.IO
import io.socket.emitter.Emitter

object NetCmd {
    private val mSocket = IO.socket(BuildConfig.HOST)

    fun initSocket(){
        mSocket.connect()
        mSocket.on("loginSuccess",object :Emitter.Listener{
            override fun call(vararg args: Any?) {
                val fuck=args[0] as Int
                Log.e("fuck",fuck.toString())
            }

        }
        )
    }

    fun send(msg:String){
        mSocket.emit("msg",msg)
    }

}