package com.lixiaoxue.demolxx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Message message = new Message();
        handler.sendMessage(message);
    }
   private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            dealMessage(msg);
        }
    };

    private void dealMessage(Message msg) {
        //

    }

    private static class MyHandle extends Handler{
        private WeakReference<TestActivity> activityWeakReference;
        public MyHandle(TestActivity activity){
            activityWeakReference = new WeakReference<TestActivity>(activity);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(activityWeakReference == null){
                return;
            }
           // activityWeakReference.todo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}