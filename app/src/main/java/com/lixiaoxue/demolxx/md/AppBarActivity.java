package com.lixiaoxue.demolxx.md;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ScrollView;

import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.base.BaseActivity;

/**
 * app:layout_scrollFlags设置，对应的值为：
 * scroll
 * enterAlways，
 * enterAlwaysCollapsed，
 * snap，
 * exitUntilCollapsed；
 *
 * https://www.jianshu.com/p/7caa5f4f49bd
 */

public class AppBarActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        //垂直方向
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        TestRecyclerAdapter testRecyclerAdapter = new TestRecyclerAdapter(this);
        recyclerView.setAdapter(testRecyclerAdapter);

    }

    @Override
    protected void initData() {

    }
}