package com.vaca.chatmygirl.net

interface UploadProgressListener {
    fun onProgress(len:Long,current:Int)
}