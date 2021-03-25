package com.lixiaoxue.demolxx.fragment;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.lixiaoxue.demolxx.DemoApplication;
import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.android.ContentProviderActivity;
import com.lixiaoxue.demolxx.android.Recycle1Activity;
import com.lixiaoxue.demolxx.android.RecycleActivity;
import com.lixiaoxue.demolxx.android.Recycler3Activity;
import com.lixiaoxue.demolxx.android.RxJavaActivity;
import com.lixiaoxue.demolxx.android.ServiceActivity;
import com.lixiaoxue.demolxx.android.TranstionActivity;
import com.lixiaoxue.demolxx.base.BaseActivity;
import com.lixiaoxue.demolxx.keep.AppKeepActivity;
import com.lixiaoxue.demolxx.utils.RefInvoke;
import com.lixiaoxue.demolxx.widget.MyView;
import com.nucarf.base.EmptyActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;

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

public class AndroidFragment extends Fragment implements View.OnClickListener {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_android, container, false);
        init();
        Button start = view.findViewById(R.id.start);
        start.setOnClickListener(this);
        Button open = view.findViewById(R.id.open);
        open.setOnClickListener(this);
        Button contentProvider = view.findViewById(R.id.contentProvider);
        contentProvider.setOnClickListener(this);
        Button service = view.findViewById(R.id.service);
        service.setOnClickListener(this);
        Button rxjava = view.findViewById(R.id.rxjava);
        rxjava.setOnClickListener(this);
        Button recycler = view.findViewById(R.id.recycler);
        recycler.setOnClickListener(this);
        Button recycler1 = view.findViewById(R.id.recycler1);
        recycler1.setOnClickListener(this);
        Button recycler2 = view.findViewById(R.id.recycler2);
        recycler2.setOnClickListener(this);
        Button transtion = view.findViewById(R.id.transtion);
        transtion.setOnClickListener(this);
        Button keepButton = view.findViewById(R.id.app_keep);
        keepButton.setOnClickListener(this);
        MyView myView = view.findViewById(R.id.myView);
        myView.start();
       // Handler handle = new Handler();
        //handle.sendMessage()


        return view;
    }


    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }

    private void init() {
      //  EncryptedSharedPreferences
        //返回这个类中的mInstrumentation字段
        Instrumentation mInstrumentation = (Instrumentation) RefInvoke.getFieldObject(Activity.class, this, "mInstrumentation");

        ClassLoader cl = BaseActivity.class.getClassLoader();
        Proxy.newProxyInstance(cl, BaseActivity.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(this,args);
            }
        });

    }


    @Override
    public void onClick(View v) {
        Context c  = DemoApplication.mINSTANCE.getBaseContext();

        switch (v.getId()){
            case R.id.start:
                Intent startIntent = new Intent(c,RecycleActivity.class);
               // startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(startIntent);
                break;
            case R.id.open:
                //第一种方法清单文件注册，然后直接使用常规方法调用
                /**
                 * openIntent = new Intent(getActivity(), EmptyActivity.class);
                 *                 startActivity(openIntent);
                 */
                //第二种方法，隐式调用，不需要当前清单文件注册，需要在目标清单文件配置Action和 Category

                /*
                Intent openIntent = new Intent();

                openIntent.setAction("com.nucarf.base.LoginInfo");
                openIntent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(openIntent);
                */
                //第三种，根据全类名打开
                Intent openIntent = new Intent(getActivity(),com.nucarf.base.EmptyActivity.class);
                startActivity(openIntent);


                break;
            case R.id.contentProvider:
                Intent contentIntent = new Intent(getActivity(), ContentProviderActivity.class);
                startActivity(contentIntent);
                break;
            case R.id.service:
                Intent serviceIntent = new Intent(getActivity(), ServiceActivity.class);
                startActivity(serviceIntent);
                break;
            case R.id.rxjava:
                Intent intent = new Intent(getActivity(), RxJavaActivity.class);
                startActivity(intent);
                break;
            case R.id.recycler:
                Intent intent1 = new Intent(getActivity(), RecycleActivity.class);
                startActivity(intent1);
                break;
            case R.id.recycler1:
                Intent intent2 = new Intent(getActivity(), Recycle1Activity.class);
                startActivity(intent2);
                break;
            case R.id.recycler2:
                Intent intent3 = new Intent(getActivity(), Recycler3Activity.class);
                startActivity(intent3);
                break;
            case R.id.transtion:
                Intent intent4 = new Intent(getActivity(), TranstionActivity.class);
                startActivity(intent4);
                break;
            case R.id.app_keep:
                Intent intent5 = new Intent(getActivity(), AppKeepActivity.class);
                startActivity(intent5);
                break;
        }




    }
}
