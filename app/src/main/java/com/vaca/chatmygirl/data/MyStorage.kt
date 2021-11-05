package com.vaca.chatmygirl.data

import com.blankj.utilcode.util.SPUtils
import com.vaca.chatmygirl.bean.Account
import com.vaca.chatmygirl.net.NetCmd

object MyStorage {

    private const val UserName="UserName"
    private const val UserPassword="UserPassword"
    var keyboardHeightX=0
    init {
        keyboardHeightX= getKeyboardHeight()
    }


    fun getAccount():Account{
        val a=SPUtils.getInstance().getString(UserName,"")
        val b=SPUtils.getInstance().getString(UserPassword,"")
        return Account(a,b)
    }


    fun setAccount(account: Account){
        NetCmd.haveRegister=true
        SPUtils.getInstance().put(UserName,account.user)
        SPUtils.getInstance().put(UserPassword,account.password)
    }

    fun getKeyboardHeight():Int{
        return SPUtils.getInstance().getInt("keyboardheight",900)
    }

    fun setKeyboardHeight(a:Int){
        keyboardHeightX=a
        SPUtils.getInstance().put("keyboardheight",a)
    }

}