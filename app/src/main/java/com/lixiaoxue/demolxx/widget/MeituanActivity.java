package com.lixiaoxue.demolxx.widget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.md.TestRecyclerAdapter;
import com.nucarf.base.utils.LogUtils;

public class MeituanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_meituan);

        initView();
    }

    private void initView() {
        MeituanSwipeRefreshLayout swiperefreshLayout = findViewById(R.id.swiperefreshLayout);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        TestRecyclerAdapter testRecyclerAdapter = new TestRecyclerAdapter(this);
        recyclerView.setAdapter(testRecyclerAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e("swiperefreshLayout执行了");
                        swiperefreshLayout.completed();
                    }
                });
            }
        },5000);

    }
}