package com.vaca.chatmygirl.bean

class Account(val user:String, val password:String){
    fun isEmpty():Boolean{
        if(user.isNullOrEmpty()){
            return true
        }
        if(password.isNullOrEmpty()){
            return true
        }
        return false
    }
}

