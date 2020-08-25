package com.lixiaoxue.demolxx.md;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lixiaoxue.demolxx.R;
import com.nucarf.base.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.OnClick;

/**
 * 要想使用BottomSheetBehavior，父View必须是CoordinatorLayout
 *
 CoordinatorLayout自带的 layout_behavior有两个,处理折叠滑动的  appbar_scrolling_view_behavior  和  处理BottomSheet的 bottom_sheet_behavior ;

 app:behavior_hideable //设置为true表示底部弹出框可隐藏
 app:behavior_peekHeight //窥视高度 , 就是BottomSheet折叠后的最小显示高度
 app:behavior_skipCollapsed //如果app:behavior_hideable设置为true，并且behavior_skipCollapsed设置为true , 则它没有折叠状态。

 STATE_COLLAPSED：折叠状态,就是peekHeight 设置的窥视高度
 STATE_EXPANDED：完全展开 3
 STATE_DRAGGING：拖动中
 STATE_SETTLING：拖动/滑动手势后，将稳定到特定高度。如果用户操作导致底部页面隐藏，则这将是峰值高度，扩展高度或0。
 STATE_HIDDEN：隐藏 5
 */
public class BottomSheetActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = BottomSheetActivity.class.getSimpleName();
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bottom_sheet);
        initView();
    }


    private void initView() {
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.design_bottom_sheet1));
        //设置默认先隐藏
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        View cardView = findViewById(R.id.cardview);
        cardView.setOnClickListener(this);

        //设置监听事件
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //拖动
                LogUtils.e(TAG,"bottomSheetBehavior = onStateChanged="+newState);
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //状态变化
                LogUtils.e(TAG,"bottomSheetBehavior = onSlide="+slideOffset);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cardview:
                //根据状态不同显示隐藏
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;
        }

    }
}