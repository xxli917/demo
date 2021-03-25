package com.lixiaoxue.demolxx.android

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.nucarf.base.utils.LogUtils

/**
 *    author : lixiaoxue
 *    date   : 2020/12/1/11:18 AM
 */
class MyService :Service() {
    val TAG = "MyService"
    private val binder = MyBinder()

    override fun onBind(intent: Intent?): IBinder {
        LogUtils.e(TAG,"onBind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtils.e(TAG,"onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    //首次创建服务时，系统会（在调用 onStartCommand() 或 onBind() 之前）调用此方法来执行一次性设置程序。如果服务已在运行，则不会调用此方法。
    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        LogUtils.e(TAG,"onDestroy")

        super.onDestroy()
    }
    inner class MyBinder: Binder(){
        fun getService(): MyService = this@MyService

    }
}