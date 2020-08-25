package com.nucarf.base.retrofit.logiclayer;

import com.nucarf.base.utils.ToastUtils;

/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public class BaseResultOld<T> {




    private T info;
    private String result;
    private String error;




    public T getData() {
        return info;
    }

    public void setData(T data) {
        this.info = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccessed() {
        try {
            if (result.equals("succ")) {
                return true;
            } else {
                ToastUtils.showShort( getError() + "");
                return false;
            }
        } catch (Exception e) {
            ToastUtils.showShort( "网络错误");
            return false;
        }
    }



}
