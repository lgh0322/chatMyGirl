package com.vaca.netdisk.net

interface UploadProgressListener {
    fun onProgress(len:Long,current:Int)
}