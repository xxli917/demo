package com.lixiaoxue.demolxx.net;


import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.nucarf.base.retrofit.LoginEvent;
import com.nucarf.base.retrofit.logiclayer.BaseResult;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.SharePreUtils;
import com.nucarf.base.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;


/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 * 外观模式
 */
public abstract class HttpCallBack<T> implements Callback<BaseResult<T>> {

    //对应HTTP的状态码
    private static final String UNAUTHORIZED = "401";
    private static final String FORBIDDEN = "403";
    private static final String NOT_FOUND = "404";
    private static final String TOKEN_INVALID = "403001";


    @Override
    public void onResponse(Call<BaseResult<T>> call, Response<BaseResult<T>> response) {
        try {
            if (response.isSuccessful() && response.body() != null && !FORBIDDEN.equals(response.body().getCode()) && !TOKEN_INVALID.equals(response.body().getCode())) {
                LogUtils.e("-------执行了");
                try {
                    succeeded(response.body());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (NOT_FOUND.equals(response.code()+"")) {
                    ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "找不到指定的链接!", 0);
                    failed(response.body().getCode());
                } else if (UNAUTHORIZED.equals(response.code()+"")) {
                    ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "系统错误!", 0);
                    failed(response.body().getCode());
                } else if (response.body() != null && response.body().getCode().equals(FORBIDDEN)) {
                    LogUtils.e("-------执行了="+response.body().getCode());
                    ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "无权限使用该系统！", 0);
                    SharePreUtils.getInstance().setToken("");
                   // EventBus.getDefault().post(new LoginEvent(LoginActivity.class));
                    failed(response.body().getCode());
                } else if (response.body() != null && response.body().getCode().equals(TOKEN_INVALID)) {
                    LogUtils.e("-------执行了="+response.body().getCode());
                    ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "登录已过期，请重新登录！", 0);
                    SharePreUtils.getInstance().setToken("");
                   // EventBus.getDefault().post(new LoginEvent(LoginActivity.class));
                    failed(response.body().getCode());
                    LogUtils.e("-------执行了="+response.body().getCode()+"------");
                }else {
                    if(response.body() != null){
                        failed(response.body().getCode());
                    }else{
                        failed(response.message());
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            failed(RetrofitConfig.ERROR_PARSE);
        }

    }

    @Override
    public void onFailure(Call<BaseResult<T>> call, Throwable e) {
        if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "解析错误!", 0);
            LogUtils.e(e.getMessage());
            failed(RetrofitConfig.ERROR_PARSE);
            return;
        }

        if (e instanceof HttpException) {
            if (UNAUTHORIZED.equals(((HttpException) e).code()+"") ) {
                ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "系统错误!", 0);
            } else if (FORBIDDEN.equals(((HttpException) e).code()+"")) {
                ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "您的账号已在其他设备登录", 0);
                SharePreUtils.getInstance().setToken("");
              //  EventBus.getDefault().post(new LoginEvent(LoginActivity.class));
            } else if (NOT_FOUND.equals(((HttpException) e).code()+"")) {
                ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "找不到指定的链接!", 0);
            } else {
                ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "网络错误!", 0);
            }
            failed(RetrofitConfig.ERROR_NETWORK);
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "请求超时!", 0);
            failed(RetrofitConfig.ERROR_NETWORK);

        } else if (e instanceof ConnectException) {
            ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "连接服务器失败,请检查网络!", 0);
            failed(RetrofitConfig.ERROR_NETWORK);

        } else if (e instanceof SocketException) {
            //  ToastUtils.show_middle_pic(R.mipmap.icon_toast_error, "网络中断，请检查网络!", 0);
            failed(RetrofitConfig.ERROR_NETWORK);

        } else if (e instanceof UnknownHostException) {
            LogUtils.e("无网络");
            ToastUtils.show_middle_pic(com.nucarf.base.R.mipmap.icon_toast_error, "连接服务器失败,请稍候重试", 0);
            failed(RetrofitConfig.ERROR_NETWORK);

        } else {
            // ToastUtils.show_middle_pic(R.mipmap.icon_toast_error, e.getMessage() + "", 0);
            failed(RetrofitConfig.ERROR_NETWORK);
        }

        LogUtils.e("错误日志", e.getMessage() + "错误");

    }


    //下面只是业务逻辑的成功和失败
    public abstract void succeeded(BaseResult<T> result);

    public abstract void failed(String errorcode);


}
