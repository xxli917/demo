package com.lixiaoxue.demolxx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lixiaoxue.demolxx.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.security.crypto.EncryptedSharedPreferences;

/**
 * 加载网络失败页面和成功页面切换
 * https://github.com/HuangJAndroid/SwipeLoadDataLayout
 * 应用数据存储和文件
 * https://developer.android.google.cn/guide/topics/data
 * 方式：
 * 文件存储：内部存储 外部存储
 * 共享首选项 ： SharedPreferences  EncryptedSharedPreferences
 * https://developer.android.google.cn/training/data-storage/shared-preferences
 * 数据库
 *
 * android 10 引入分区储存
 * https://developer.android.google.cn/training/data-storage#scoped-storage
 */

public class AndroidFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_android, container, false);
        init();

        return view;
    }

    private void init() {
      //  EncryptedSharedPreferences

    }


}
