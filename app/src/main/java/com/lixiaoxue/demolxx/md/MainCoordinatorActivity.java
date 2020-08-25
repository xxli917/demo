package com.lixiaoxue.demolxx.md;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.base.BaseActivity;
import com.nucarf.base.utils.ToastUtils;

/**
 * Snackbar
 * https://www.jianshu.com/p/f38a20b8aa32
 */

public class MainCoordinatorActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);

        CollapsingToolbarLayout layout = findViewById(R.id.collaps_toolbar_layout);
        //设置收起颜色
        layout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorAccent));
        int color = getResources().getColor(R.color.colorPrimary);
        //设置展开颜色
        layout.setExpandedTitleColor(color);

        FloatingActionButton floatingActionButton = findViewById(R.id.float_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //要从中查找父级的视图。此视图还用于在以下情况下查找定位视图：
                //*正在调用{@link Snackbar\setAnchorView（int）}。 键盘弹起，会在这个视图下面显示
                Snackbar.make(layout, "您收到一条新消息，请注意查收，请打开消息列表，哈哈哈哈", Snackbar.LENGTH_INDEFINITE)
                        .setAction("点击", new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                ToastUtils.show_middle(getApplicationContext(),"点击了",ToastUtils.LENGTH_SHORT);

                            }
                        })
                        .addCallback(new Snackbar.Callback(){
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                            }

                            @Override
                            public void onShown(Snackbar sb) {
                                super.onShown(sb);
                            }
                        })
                        .show();
            }
        });

    }


    @Override
    protected void initData() {

    }


}