package com.lixiaoxue.demolxx.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.md.AppBarActivity;
import com.lixiaoxue.demolxx.md.BottomSheetActivity;
import com.lixiaoxue.demolxx.md.MainCoordinatorActivity;
import com.lixiaoxue.demolxx.md.LoginActivity;
import com.lixiaoxue.demolxx.md.MotionActivity;
import com.lixiaoxue.demolxx.md.MySettingsActivity;
import com.lixiaoxue.demolxx.md.ToolbarActivity;
import com.lixiaoxue.demolxx.utils.ClickUtils;
import com.nucarf.base.utils.DialogFragmentUtils;

/**
 * A fragment representing a list of Items.
 */
public class MateralDesignFragment extends Fragment implements View.OnClickListener {


    private View view;
    private View bottomSheet;

    public MateralDesignFragment() {
    }


    public static MateralDesignFragment newInstance() {
        MateralDesignFragment fragment = new MateralDesignFragment();
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_materal_design, container, false);
        initView();
        return view;
    }

    private void initView() {
        View mainView = view.findViewById(R.id.main_view);
        View loginView = view.findViewById(R.id.login_view);
        View appView = view.findViewById(R.id.app_view);
        View motionEditor = view.findViewById(R.id.motion_editor);
        View dialogView = view.findViewById(R.id.dialog);
        View toolbar = view.findViewById(R.id.toolbar);
        bottomSheet = view.findViewById(R.id.bottom_sheet);
        bottomSheet.setOnClickListener(this);
        View settingView = view.findViewById(R.id.setting);
        toolbar.setOnClickListener(this);
        dialogView.setOnClickListener(this);
        loginView.setOnClickListener(this);
        mainView.setOnClickListener(this);
        appView.setOnClickListener(this);
        motionEditor.setOnClickListener(this);
        settingView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_view:
                if(ClickUtils.isFastClick()){
                    Intent intent = new Intent(getContext(), MainCoordinatorActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.app_view:
                if(ClickUtils.isFastClick()){
                    Intent appIntent = new Intent(getContext(), AppBarActivity.class);
                    startActivity(appIntent);
                }
                break;
            case R.id.login_view:
                Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.motion_editor:
                Intent motionIntent = new Intent(getContext(), MotionActivity.class);
                startActivity(motionIntent);
                break;
            case R.id.dialog:
                DialogFragmentUtils.showDoubleDialog(getActivity());

                break;
            case R.id.toolbar:
                Intent toolbarIntent = new Intent(getContext(), ToolbarActivity.class);
                startActivity(toolbarIntent);
                break;
            case R.id.bottom_sheet:
                Intent bottomIntent = new Intent(getContext(), BottomSheetActivity.class);
                startActivity(bottomIntent);
                break;
            case R.id.setting:
                Intent settingIntent = new Intent(getContext(), MySettingsActivity.class);
                startActivity(settingIntent);
                break;

        }

    }
}