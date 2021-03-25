package com.lixiaoxue.demolxx.keep;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.renderscript.ScriptC;
import android.util.Log;

/**
 * author : lixiaoxue
 * date   : 2021/3/19/10:27 AM
 */
public class ScreenReceiverUtil {
    public Context mContext;
    public OnePixelReceiver receiver;

    public ScreenReceiverUtil(Context context) {
        mContext = context;
        registerReceiver();
    }

    private ScreenStateListener mScreenListenerer;

    public void setScreenReceiverListener(ScreenStateListener screenListenerer) {
        mScreenListenerer = screenListenerer;
    }

    public void registerReceiver() {
        receiver = new OnePixelReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mContext.registerReceiver(receiver, filter);
    }

    public void cancelReceiver() {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
        }
    }


    public interface ScreenStateListener {
         void onScreenOn();

         void onScreenOff();

         void onUserPresent();
    }

    public class OnePixelReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //屏幕关闭启动1像素Activity
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.e("OnePx", "屏幕关闭 打开1像素");

                if (mScreenListenerer != null) {
                    mScreenListenerer.onScreenOff();
                }

                //屏幕打开 结束1像素
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.e("OnePx", "屏幕打开 结束1像素");
                if (mScreenListenerer != null) {
                    mScreenListenerer.onScreenOn();
                }
            }
            else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) { // 解锁
                if (mScreenListenerer != null) {
                    mScreenListenerer.onUserPresent();
                }
            }
        }
    }
}
