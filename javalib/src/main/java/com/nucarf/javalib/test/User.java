package com.nucarf.javalib.test;

/**
 * author : lixiaoxue
 * date   : 2020/12/14/9:39 AM
 */
public class User {
    public final int size = 10000;
    public double[] arr = new double[size];
    public String id;
    public User(String id){
        this.id = id;

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println(id+"被回收了");
    }
}
