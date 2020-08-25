package com.lixiaoxue.demolxx.ad;

import java.io.Serializable;

public class AdMessageBean implements Serializable {
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    private String imgUrl;
    private String adUrl;
    private boolean isShow;//是否显示广告

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
