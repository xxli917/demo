package com.lixiaoxue.demolxx.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.md.TestRecyclerAdapter;
import com.nucarf.base.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GitFragment extends Fragment {
    private String TAG = GitFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_git, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        SwipeRefreshLayout layout = view.findViewById(R.id.swipeLoadDataLayout);
        layout.setColorSchemeResources(R.color.colorPrimary);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtils.e(TAG,"onRefresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);

                    }
                },3000);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        //创建线性布局
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        //垂直方向
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerView.setLayoutManager(mLayoutManager);
        TestRecyclerAdapter testRecyclerAdapter = new TestRecyclerAdapter(getContext());
        recyclerView.setAdapter(testRecyclerAdapter);

    }
}
