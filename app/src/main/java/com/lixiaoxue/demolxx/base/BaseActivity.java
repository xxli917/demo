package com.lixiaoxue.demolxx.base;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.retrofit.LoginEvent;
import com.nucarf.base.utils.UiGoto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.internal.CustomAdapt;

//import com.umeng.analytics.MobclickAgent;


public abstract class BaseActivity extends AppCompatActivity implements CustomAdapt {

    public String TAG = BaseActivity.class.getSimpleName();
    protected Context mContext;

    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //去除自带标题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //状态栏适配
        ImmersionBar.with(this)
                //状态栏字体是深色，不写默认为亮色
                .statusBarDarkFont(true, 0.2f)
                //适配魅族手机 修改 flyme OS 状态栏字体颜色
                .flymeOSStatusBarFontColor(com.nucarf.base.R.color.black)
                //解决布局与状态栏重叠问题
                .fitsSystemWindows(getFitsSystemWindows())
                //解决软键盘与底部输入框冲突问题
                .keyboardEnable(true)
                .init();
      //  ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).titleBar(titleBar).init();

        //放到onStart 有可能事件已经发出，但是还没有注册，
        registerEventBus();

    }

    //是否状态栏重叠问题，true =不重贴，也就是不沉浸式
    public boolean getFitsSystemWindows(){
        return false;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
        initData();
    }

    protected abstract void initData();

    /**
     * 注册EventBus通信组件
     */
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
    }

    /**
     * 取消注册EventBus通信组件
     */
    protected void unRegisterEventBus() {
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object event) {
       if (event instanceof LoginEvent) {
            UiGoto.startAty(this, ((LoginEvent)event).getEventClass());
            finish();
        }
    }

    protected String getName() {
        return getClass().getSimpleName();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //统计页面(仅有Activity的应用中SDK自动调用，不需要单独写。"SplashScreen"为页面名称，可自定义)
      //  MobclickAgent.onPageStart(getClass().getName());
       // MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPageEnd(getClass().getName());
        //MobclickAgent.onPause(this);
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }
        ImmersionBar.with(this).destroy();
        //放到onStop中也不会解决同一事件，两个页面同时接受处理两次的问题 因为onStop在onStart后执行
        unRegisterEventBus();



    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 0;
    }

    //禁止跟随系统字体大小调节
    @Override
    public Resources getResources() {//还原字体大小
        Resources res = super.getResources();
        Configuration configuration = res.getConfiguration();
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }
        return res;
    }


}