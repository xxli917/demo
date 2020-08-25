package com.lixiaoxue.demolxx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.widget.EmptyRefreshView;
import com.nucarf.base.utils.DialogUtils;
import com.nucarf.base.utils.ToastUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class JavaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_java, container, false);
        return view;
    }
}
