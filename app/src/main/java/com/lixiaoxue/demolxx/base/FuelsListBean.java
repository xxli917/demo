package com.lixiaoxue.demolxx.base;

import java.io.Serializable;
import java.util.List;

public class FuelsListBean implements Serializable {
    /**
     * type : 1
     * name : 柴油
     * fuel_info : [{"fuel_no":"99","name":"最牛柴油#"},{"fuel_no":"1005","name":"89#"},{"fuel_no":"1020","name":"92#"},{"fuel_no":"1030","name":"93#"},{"fuel_no":"1040","name":"95#"},{"fuel_no":"1050","name":"97#"},{"fuel_no":"1090","name":"98#"}]
     */

    private String type;
    private String name;
    private List<FuelsBean> fuel_info;

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

    public List<FuelsBean> getFuel_info() {
        return fuel_info;
    }

    public void setFuel_info(List<FuelsBean> fuel_info) {
        this.fuel_info = fuel_info;
    }
}
