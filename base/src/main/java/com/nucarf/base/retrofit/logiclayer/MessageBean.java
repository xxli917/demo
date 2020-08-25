package com.nucarf.base.retrofit.logiclayer;

import java.io.Serializable;

public class MessageBean implements Serializable {


    /**
     * new_user : 0
     * wechat : {"appid":"wxdd2130638dac1044","appsecret":"04b4095002cb4ebccaede6610bda6870","debug":true,"logcallback":"wechat_write","token":"ncarf_wechat_token"}
     */

    private String new_user;
    private WechatBean wechat;

    public String getNew_user() {
        return new_user;
    }

    public void setNew_user(String new_user) {
        this.new_user = new_user;
    }

    public WechatBean getWechat() {
        return wechat;
    }

    public void setWechat(WechatBean wechat) {
        this.wechat = wechat;
    }

    public static class WechatBean {
        /**
         * appid : wxdd2130638dac1044
         * appsecret : 04b4095002cb4ebccaede6610bda6870
         * debug : true
         * logcallback : wechat_write
         * token : ncarf_wechat_token
         */

        private String appid;
        private String appsecret;
        private boolean debug;
        private String logcallback;
        private String token;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAppsecret() {
            return appsecret;
        }

        public void setAppsecret(String appsecret) {
            this.appsecret = appsecret;
        }

        public boolean isDebug() {
            return debug;
        }

        public void setDebug(boolean debug) {
            this.debug = debug;
        }

        public String getLogcallback() {
            return logcallback;
        }

        public void setLogcallback(String logcallback) {
            this.logcallback = logcallback;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
