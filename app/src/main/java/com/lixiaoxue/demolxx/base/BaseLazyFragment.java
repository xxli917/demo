package com.lixiaoxue.demolxx.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.gyf.barlibrary.ImmersionBar;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
//import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2018/5/5.
 * 一般我们会在onActivityCreated时加载数据，
 * viewpager 默认会预加载，这时我们会默认加载多个页面数据，可能造成卡顿
 * 为解决这个问题，我们使用懒加载，在页面显示的时候才加载数据，
 * 这时候页面显示就会加载新数据
 * 部分页面不需要每次都需要刷新，那么我们手动设置标识，如果不需要，就不进行加载
 */

public abstract class BaseLazyFragment extends Fragment {
    protected Activity mActivity;
    protected View mRootView;

    /**
     * 是否对用户可见
     */
    protected boolean mIsVisible;
    /**
     * 是否加载完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean mIsPrepare;



    /**
     * 是否加载完成
     * 当执行完onViewCreated方法后即为true
     */
    protected boolean mIsImmersion;

    protected ImmersionBar mImmersionBar;

    private Unbinder unbinder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        if (isLazyLoad()) {
            mIsPrepare = true;
            mIsImmersion = true;
            onLazyLoad();
        } else {
            initData();
            if (isImmersionBarEnabled())
                initImmersionBar();
        }

        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        //MobclickAgent.onPageStart(getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
       // MobclickAgent.onPageEnd(getClass().getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    /**
     * 是否懒加载 页面可见时才加载数据
     *
     * @return the boolean
     */
    protected boolean isLazyLoad() {
        return true;
    }
    /**
     * 是否页面可见就刷新数据
     */

    public boolean isNeedReload(){
        return true;
    }


    /**
     * 是否在Fragment使用沉浸式
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /**
     * 用户可见时执行的操作
     */
    protected void onVisible() {
        onLazyLoad();
    }

    private void onLazyLoad() {
        if (mIsVisible  && mIsPrepare) {
            mIsPrepare = false;
            initData();
        }
        if (mIsVisible && mIsImmersion && isImmersionBarEnabled()) {
            initImmersionBar();
        }
    }

    /**
     * Sets layout id.
     *
     * @return the layout id
     */
    protected abstract int setLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.keyboardEnable(false).navigationBarWithKitkatEnable(false).init();
    }

    /**
     * view与数据绑定
     */
    protected abstract void initView();

    /**
     * 设置监听
     */
    protected void setListener() {

    }

    /**
     * 用户不可见执行
     */
    protected void onInvisible() {

    }

    /**
     * 找到activity的控件
     *
     * @param <T> the type parameter
     * @param id  the id
     * @return the t
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T findActivityViewById(@IdRes int id) {
        return (T) mActivity.findViewById(id);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
