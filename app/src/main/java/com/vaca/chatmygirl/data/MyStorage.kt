package com.vaca.chatmygirl.data

import com.blankj.utilcode.util.SPUtils

object MyStorage {

    val UserName="UserName"
    val UserPassword="UserPassword"

    fun getAccount(){
        return SPUtils.getInstance().getString("")

    }



}