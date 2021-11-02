package com.vaca.chatmygirl.net

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.vaca.chatmygirl.BuildConfig
import com.vaca.chatmygirl.data.MyStorage
import io.socket.client.IO
import io.socket.emitter.Emitter
import org.json.JSONObject

object NetCmd {
    private val mSocket = IO.socket(BuildConfig.HOST)

    private val PURPOSE = "purpose"

    val loginState=MutableLiveData<Boolean>()
    var myAcount=MyStorage.getAccount()
    var haveRegister=!myAcount.isEmpty()






    fun initSocket() {
        mSocket.connect()
        mSocket.on("Server"
        ) { args ->
            val content = JSONObject(args[0].toString())

            when (content.getString("purpose")) {
                "connectSuccess" -> {
                    if (haveRegister) {
                        Log.i("girlxx", "haveRegister")
                        NetCmd.login(myAcount.user, myAcount.password)
                    } else {
                        Log.i("girlxx", "NothaveRegister")
                    }
                }
                "login"->{
                    val result=content.getInt("result").toString()
                    LiveEventBus.get("login",String::class.java).post(result)
                }
                "signup"->{
                    val result=content.getInt("result").toString()
                    LiveEventBus.get("signUp",String::class.java).post(result)
                }
            }
        }

        mSocket.on("Relay"
        ) { args ->
            val fuck = args[0]
            Log.e("fuck", fuck.toString())
        }
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