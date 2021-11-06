package com.vaca.chatmygirl.net

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.vaca.chatmygirl.BuildConfig
import com.vaca.chatmygirl.bean.MyInfo
import com.vaca.chatmygirl.data.MyStorage
import io.socket.client.IO
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.util.*

object NetCmd {
    val netAddress=BuildConfig.HOST
    private val mSocket = IO.socket(netAddress)
    private val client = OkHttpClient();
    private val PURPOSE = "purpose"

    val loginState=MutableLiveData<Boolean>()
    var myAcount=MyStorage.getAccount()
    var haveRegister=!myAcount.isEmpty()
    var currentChatId="56"
    val dataScope = CoroutineScope(Dispatchers.IO)
    val myInfo=MutableLiveData<MyInfo>()

    //-------------------------------------------------------------------------------用户信息上传
    fun getInfo(): String? {
        try {
            val url = "$netAddress/info"
            val request: Request = Request.Builder()
                .addHeader("Content-Type", "application/json; charset=UTF-8")
                .addHeader("acount", myAcount.user)
                .url(url)
                .build()
            client.newCall(request)
                .execute()
                .use { response ->
                    response.body?.string()?.let {
                        Log.e("sdf",it)
                        return it
                    }
                }
            return null
        }catch (e:Exception){
            return null
        }

    }




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


    fun chatMsg(sendMsg:String, type:Int, isGroup:Boolean){
        val msg=JSONObject()
        msg.put(PURPOSE,"chat")
        msg.put("msg",sendMsg)
        msg.put("time",System.currentTimeMillis())
        msg.put("type",type)
        msg.put("from", myAcount.user)
        msg.put("to", currentChatId)
        Log.e("fucsssk",msg.toString())
        sendRelay(msg.toString())
    }

    fun chatText(msg:String,isGroup: Boolean){
        chatMsg(msg,1,isGroup)
    }

}