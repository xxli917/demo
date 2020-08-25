package com.nucarf.base.utils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.nucarf.base.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * 创建 DialogFragment 有两种方式：
 * 覆写其 onCreateDialog 方法 — ① 利用AlertDialog或者Dialog创建出Dialog。
 * 覆写其 onCreateView 方法 — ② 使用定义的xml布局文件展示Dialog。
 * 虽然这两种方式都能实现相同的效果，但是它们各有自己适合的应用场景：
 * 方法 ①，一般用于创建替代传统的 Dialog 对话框的场景，UI 简单，功能单一。
 * 方法 ②，一般用于创建复杂内容弹窗或全屏展示效果的场景，UI 复杂，功能复杂，一般有网络请求等异步操作。
 * 另外它又是Fragment，所以当旋转屏幕和按下后退键时可以更好的管理其声明周期，它和Fragment有着基本一致的声明周期。
 *
 *
 *
 */

public class MyDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
       // params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        //params.windowAnimations = R.style.bottomSheet_animation;
        getDialog().getWindow().setAttributes(params);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        return view;    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ScreenUtil.getScreenWidth(getActivity()), ScreenUtil.dip2px(165));

    }
}
