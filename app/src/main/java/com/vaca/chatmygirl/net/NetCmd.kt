package com.vaca.chatmygirl.net

import com.vaca.chatmygirl.BuildConfig
import io.socket.client.IO

object NetCmd {



    val mSocket= IO.socket(BuildConfig.HOST)

}