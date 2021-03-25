package com.lixiaoxue.demolxx.keep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.lixiaoxue.demolxx.Contants;
import com.lixiaoxue.demolxx.MainActivity;
import com.lixiaoxue.demolxx.R;
import com.nucarf.base.utils.AndroidUtil;

public class SinglePixelActivity extends AppCompatActivity {
    public static String TAG = "SinglePixelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Contants.DEBUG)
            Log.d(TAG,"onCreate--->启动1像素保活");
        // 获得activity的Window对象，设置其属性
        Window mWindow = getWindow();
        mWindow.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams attrParams = mWindow.getAttributes();
        attrParams.x = 0;
        attrParams.y = 0;
        attrParams.height = 1;
        attrParams.width = 1;
        mWindow.setAttributes(attrParams);
        // 绑定SinglePixelActivity到ScreenManager
        ScreenManager.getScreenManagerInstance(this).setSingleActivity(this);
    }

    @Override
    protected void onDestroy() {
        if(Contants.DEBUG)
            Log.d(TAG,"onDestroy--->1像素保活被终止");
        if(!AndroidUtil.isProcessExist(getApplicationContext(),android.os.Process.myPid())){
            Intent intentAlive = new Intent(this, MainActivity.class);
            intentAlive.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentAlive);
            Log.i(TAG,"SinglePixelActivity---->APP被干掉了，我要重启它");
        }
        super.onDestroy();
    }
}