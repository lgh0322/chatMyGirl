package com.vaca.chatmygirl.event

import android.util.Log
import com.jeremyliao.liveeventbus.LiveEventBus

object GoGo {
    fun goChat(){
        Log.e("chat","gotoChat")
        LiveEventBus.get("rootGo",String::class.java).post("chat")
    }
}