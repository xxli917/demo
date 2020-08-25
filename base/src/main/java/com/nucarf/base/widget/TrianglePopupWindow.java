package com.nucarf.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nucarf.base.R;


public class TrianglePopupWindow extends PopupWindow {
    private Context context;
    private RecyclerView recyclerView;
    BaseQuickAdapter adapter;

    public TrianglePopupWindow(Context context) {
        super(context);
        this.context = context;
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.triangle_popupwindow, null);
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        setContentView(view);
        initWindow();
    }
    public void setAdapter(BaseQuickAdapter adapter){
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
    }
    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener){
        if(adapter == null){
            throw new IllegalStateException("please setAdapter first");
        }
        adapter.setOnItemClickListener(listener);
    }

    private void initWindow() {
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha((Activity) context, 0.8f);//0.0-1.0
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((Activity) context, 1f);
            }
        });
    }

    //设置添加屏幕的背景透明度
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public void showAtBottom(View view) {
        //弹窗位置设置
//        showAsDropDown(view, Math.abs((view.getWidth() - getWidth()) / 2), 10);
        showAsDropDown(view, 0, 10);
        //showAtLocation(view, Gravity.TOP | Gravity.RIGHT, 10, 110);//有偏差
    }

}
