package com.lixiaoxue.demolxx.launchstarter.task;

public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
