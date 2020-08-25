package com.lixiaoxue.demolxx.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nucarf.base.utils.LogUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by tangh on 14-4-21.
 * Fragment基类
 * 提供一个布局id即可创建一个fragment
 */
public abstract class BaseFragment extends Fragment {


    /**
     * 全局activity
     */
    protected FragmentActivity mContext;

    protected FragmentManager mFragmentManager;

    protected View rootView;
    private Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = getActivity();
        LogUtils.d(getClass().getSimpleName(), "onAttach");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentManager = mContext.getSupportFragmentManager();
        LogUtils.d(getClass().getSimpleName(), "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentView() != 0) {
            if (rootView == null) {
                rootView = inflater.inflate(getContentView(), container, false);
                unbinder = ButterKnife.bind(rootView);
                initView(rootView);
            } else {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                if (parent != null) {
                    parent.removeView(rootView);
                }
            }
            return rootView;
        }
        return null;
    }

    public abstract int getContentView();


    public abstract void initView(View view);

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.d(getClass().getSimpleName(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
      if(null!=unbinder) {
          unbinder.unbind();
      }
    }

    /**
     * 将resid布局替换成fragment
     *
     * @param resid
     * @param fragment
     */
    public void replace(int resid, Fragment fragment) {
        mFragmentManager.beginTransaction().replace(resid, fragment).commitAllowingStateLoss();
    }

    /**
     * @param resid
     * @param fragment
     */
    public void add(int resid, Fragment fragment) {
        mFragmentManager.beginTransaction().add(resid, fragment).commitAllowingStateLoss();
    }

    /**
     * @param resid
     * @param fragment
     */
    public void add(int resid, Fragment fragment, String tag) {
        mFragmentManager.beginTransaction().add(resid, fragment, tag).commitAllowingStateLoss();
    }

    /**
     * 显示fragment
     *
     * @param fragment
     */
    public void show(Fragment fragment) {
        mFragmentManager.beginTransaction().show(fragment).commitAllowingStateLoss();
    }

    /**
     * 隐藏fragment
     *
     * @param fragment
     */
    public void hide(Fragment fragment) {
        mFragmentManager.beginTransaction().hide(fragment).commitAllowingStateLoss();
    }
}
