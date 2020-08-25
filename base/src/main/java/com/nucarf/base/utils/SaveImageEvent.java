package com.nucarf.base.utils;

class SaveImageEvent {
    private String mFromClass;

    public SaveImageEvent(String pFromClass) {
        this.mFromClass = pFromClass;
    }

    public String getmFromClass() {
        return mFromClass;
    }

    public void setmFromClass(String mFromClass) {
        this.mFromClass = mFromClass;
    }
}
