package com.lixiaoxue.demolxx.keep;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.lixiaoxue.demolxx.R;
import com.nucarf.base.utils.LogUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

//不设置 1分钟被杀死 在内存不够杀死进程时，前台进程不容易被查杀
public class GrayService extends Service {
    public static String TAG = "GrayService";
    //private final static int SERVICE_ID = 1001;
    private static final int SERVICE_ID = 1;//前台进程

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e(TAG,"onCreate");

    }

    public GrayService() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e(TAG,"onStartCommand");

        //判断版本
        if (Build.VERSION.SDK_INT < 18) {//Android4.3以下版本
            //将Service设置为前台服务，可以取消通知栏消息
            startForeground(SERVICE_ID, new Notification());

        } else if (Build.VERSION.SDK_INT < 24) {//Android4.3 - 7.0之间
            //将Service设置为前台服务，可以取消通知栏消息
            startForeground(SERVICE_ID, new Notification());
            startService(new Intent(this, InnerService.class));

        } else {//Android 8.0以上
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                NotificationChannel channel = new NotificationChannel("channel_on_id","name",NotificationManager.IMPORTANCE_NONE);
                manager.createNotificationChannel(channel);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channel");
                builder.setChannelId("channel_on_id");                //将Service设置为前台服务,Android 8.0 App启动不会弹出通知栏消息，退出后台会弹出通知消息
                //Android9.0启动时候会立刻弹出通知栏消息
                Notification notification = builder.build();
                startForeground(SERVICE_ID,notification);
                //无效
                startService(new Intent(this, InnerService.class));

            }
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG,"服务被销毁了");
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段,18
     */
    public static class InnerService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(SERVICE_ID, new Notification());
            stopForeground(true);//移除消息通知拦
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}