package com.lixiaoxue.demolxx.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.ganxin.library.SwipeLoadDataLayout;
import com.lixiaoxue.demolxx.R;
import com.nucarf.base.utils.ToastUtils;

public class SwipLoadActivity extends AppCompatActivity {
    private SwipeLoadDataLayout swipeLoadDataLayout;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swip_load);
        init();

    }

    private void init() {
       swipeLoadDataLayout = findViewById(R.id.swipeLoadDataLayout);


        //  DialogUtils.dialogPro(getContext(),"加载中",true);
        //DialogUtils.showLoadingWithImage(getContext());


        swipeLoadDataLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeLoadDataLayout.setOnReloadListener(new SwipeLoadDataLayout.OnReloadListener() {
            @Override
            public void onReload(View v, int status) {
                ToastUtils.show_middle(SwipLoadActivity.this,"重新加载",ToastUtils.LENGTH_SHORT);
            }
        });
        swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.LOADING);
        initHandler();
    }

    private void initHandler() {
        if(mHandler==null){
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case 0:
                            swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.EMPTY);
                            mHandler.sendEmptyMessageDelayed(1,2000);
                            break;
                        case 1:
                            swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.ERROR);
                            mHandler.sendEmptyMessageDelayed(2,2000);
                            break;
                        case 2:
                            swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.NO_NETWORK);
                            mHandler.sendEmptyMessageDelayed(3,2000);
                            break;
                        case 3:
                            swipeLoadDataLayout.setStatus(SwipeLoadDataLayout.SUCCESS);
                            break;
                    }
                }
            };

            mHandler.sendEmptyMessageDelayed(0,2000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler!=null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler=null;
        }
    }


}