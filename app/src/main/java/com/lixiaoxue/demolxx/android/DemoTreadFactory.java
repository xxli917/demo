package com.lixiaoxue.demolxx.android;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * author : lixiaoxue
 * date   : 2020/11/26/11:00 AM
 */
public class DemoTreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r,"name");
    }

}
