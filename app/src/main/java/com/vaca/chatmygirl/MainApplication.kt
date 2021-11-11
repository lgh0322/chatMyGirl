package com.vaca.chatmygirl

import android.app.Application

import com.tencent.bugly.crashreport.CrashReport
import com.vaca.chatmygirl.net.NetCmd
import com.vaca.chatmygirl.utils.PathUtil


class MainApplication : Application() {

    companion object {
        lateinit var application: Application
    }


    override fun onCreate() {
        super.onCreate()

        PathUtil.initVar(this)


//        NetCmd.initSocket()
        application = this
        CrashReport.initCrashReport(this, "ab96e0dbf2", false);

    }


}