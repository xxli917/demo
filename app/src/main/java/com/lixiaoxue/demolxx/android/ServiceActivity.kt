package com.lixiaoxue.demolxx.android

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import com.lixiaoxue.demolxx.R
import com.lixiaoxue.demolxx.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {
    var serviceIntent: Intent? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*registerReceiver(object: BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                TODO("Not yet implemented")
            }

        })*/
        sendBroadcast(intent)
        var dataBinding = DataBindingUtil.setContentView<ActivityServiceBinding>(this, R.layout.activity_service)
        dataBinding.start.setOnClickListener() {
            serviceIntent = Intent(this@ServiceActivity, MyService::class.java)
            startService(serviceIntent)
            var myService = MyService()
          //  myService.stopSelf()
        }
        dataBinding.stop.setOnClickListener() {
            serviceIntent?.let {
                stopService(serviceIntent)

            }
        }
       // var handler = Handler(Looper.getMainLooper())
        dataBinding.bind.setOnClickListener(){
            serviceIntent = Intent(this@ServiceActivity, MyService::class.java)

            bindService(serviceIntent,object :ServiceConnection{
                override fun onServiceDisconnected(name: ComponentName?) {
                }

                override fun onServiceConnected(name: ComponentName, service: IBinder) {
                    val binder = service as MyService.MyBinder
                   var  mService = binder.getService()
                    mService.stopSelf()
                    //unbindService()
                }

            }, Context.BIND_AUTO_CREATE)
        }



    }
}