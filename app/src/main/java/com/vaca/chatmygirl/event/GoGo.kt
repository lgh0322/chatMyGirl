package com.vaca.chatmygirl.event

import com.jeremyliao.liveeventbus.LiveEventBus

object GoGo {
    fun goChat(){
        LiveEventBus.get("rootGo",String::class.java).post("chat")
    }
}