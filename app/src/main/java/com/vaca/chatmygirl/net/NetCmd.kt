package com.vaca.chatmygirl.net

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vaca.chatmygirl.BuildConfig
import io.socket.client.IO
import io.socket.emitter.Emitter
import org.json.JSONObject

object NetCmd {
    private val mSocket = IO.socket(BuildConfig.HOST)

    private val PURPOSE = "purpose"

    val loginState=MutableLiveData<Boolean>()





    fun initSocket() {
        mSocket.connect()
        mSocket.on("Server", object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                val content = JSONObject( args[0].toString())

                val purpose=content.getString("purpose")
                when(purpose){
                    "connectSuccess"->{
                        Log.e("fuck",content.toString())
//                        NetCmd.login()
                    }
                }

            }

        }
        )

        mSocket.on("Relay", object : Emitter.Listener {
            override fun call(vararg args: Any?) {
                val fuck = args[0]
                Log.e("fuck", fuck.toString())
            }

        }
        )
    }

    private fun sendServer(msg: String) {
        mSocket.emit("Server", msg)
    }

    private fun sendRelay(msg: String) {
        mSocket.emit("Relay", msg)
    }


    fun login(user: String, password: String) {
        val msg = JSONObject()
        msg.put(PURPOSE, "login")
        msg.put("username", user)
        msg.put("password", password)
        sendServer(msg.toString())
    }

    fun signUp(user: String, password: String) {
        val msg = JSONObject()
        msg.put(PURPOSE, "signup")
        msg.put("username", user)
        msg.put("password", password)
        sendServer(msg.toString())
    }

}