package com.nucarf.base.bean;

import java.util.List;

/**
 * Created by WANG on 2016/2/24.
 */
public class PostPhotoEvent {
    private List<String> photoPath;
    private boolean isAdd;
    private String key  = "";
    public PostPhotoEvent(List<String> photoPath, boolean isAdd) {
        this.photoPath = photoPath;
        this.isAdd = isAdd;
    }

    public PostPhotoEvent(List<String> photoPath) {
        this.photoPath = photoPath;
    }


    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(List<String> photoPath) {
        this.photoPath = photoPath;
    }
}
