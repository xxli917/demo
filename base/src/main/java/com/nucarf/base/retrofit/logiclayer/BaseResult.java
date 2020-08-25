package com.nucarf.base.retrofit.logiclayer;


import android.text.TextUtils;

/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public class BaseResult<T> {
    private String code;
    private T data;
    private Object post;
    private String msg;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResult() {
        return data;
    }

    public void setResult(T result) {
        this.data = result;
    }

    public Object getPost() {
        return post;
    }

    public void setPost(Object post) {
        this.post = post;
    }

    public String getMessage() {
        if(TextUtils.isEmpty(msg)){
            return message;
        }else{
            return msg;

        }
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public boolean isSuccessed(){
        return "200".equals(code);
    }

    /*public boolean isSuccessed() {
        try {
            if (code.equals(RetrofitConfig.STATUS_NCARF_SUCCESS)) {
                if (getMessage() instanceof MessageBean) {
                    //第一次成功登陆时返回数据
                    MessageBean messageBean = (MessageBean) getMessage();
                    SharePreUtils.setIsNewUser(BaseAppCache.getContext(), !messageBean.getNew_user().equals("0"));
                }
                return true;
            } else if (code.equals(RetrofitConfig.STATUS_GOTOLOGIN)) {
                SharePreUtils.setjwt_token(BaseAppCache.getContext(), "");
                SharePreUtils.removeKey(BaseAppCache.getContext());
                EventBus.getDefault().post(new LoginEvent());
                return false;
            } else if (code.equals(RetrofitConfig.STATUS_NO_EXITS)) {
                return false;
            }else if (code.equals(RetrofitConfig.STATUS_COMPANY_OR_ID_ERROR)) {
                return false;
            } else {
                ToastUtils.show_middle_pic(R.mipmap.icon_toast_error, getMessage() instanceof String ? getMessage() + "" : "", 0);

                if (code.equals("1")) {
                    if (getMessage() instanceof String) {
                        String message = (String) getMessage();
                        if (message.equals("token_invalid")) {
                            LogUtils.e("base result" + "token_invalid");
                            SharePreUtils.setjwt_token(BaseAppCache.getContext(), "");
                            SharePreUtils.removeKey(BaseAppCache.getContext());
                            EventBus.getDefault().post(new LoginEvent());
                        }
                    }
                }
                return false;
            }
        } catch (Exception e) {
            ToastUtils.show_middle_pic(R.mipmap.icon_toast_error, "网络错误", 0);

            return false;
        }
    }*/


}
