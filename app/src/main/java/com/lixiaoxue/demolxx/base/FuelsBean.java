package com.lixiaoxue.demolxx.base;

import android.text.TextUtils;

import java.io.Serializable;

public class FuelsBean implements Serializable {
    /**
     * fuel_name : 0# 柴油
     * fuel_no : 2001
     * price : 675
     */
    private String fuel_name;
    private String fuel_no;
    private String price;
    private String discount_price;//优惠价格

    private String name;
    private String effect_time;
    private boolean isChoice = false;
    // 油品筛选时候使用
    private String type;//4 天然气 1汽油 2 柴油3其他
    private String parent_name;

    public String getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(String discount_price) {
        this.discount_price = discount_price;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChoice() {
        return isChoice;
    }

    public void setChoice(boolean choice) {
        isChoice = choice;
    }

    public String getEffect_time() {
        return effect_time;
    }

    public void setEffect_time(String effect_time) {
        this.effect_time = effect_time;
    }

    public String getFuel_name() {
        return fuel_name;
    }

    public void setFuel_name(String fuel_name) {
        this.fuel_name = fuel_name;
    }

    public String getFuel_no() {
        return fuel_no;
    }

    public void setFuel_no(String fuel_no) {
        this.fuel_no = fuel_no;
    }

    public String getPrice() {
        if(TextUtils.isEmpty(price)) {
            return "0";
        }
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
