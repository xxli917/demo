package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2021/3/18/11:56 AM
 */
public class TestD {
    public static void main(String[] args){
        int mMsgId = 0xFFFF + (int) (Math.random() * 0xFFFF);
        System.out.println("msgId="+mMsgId);

    }
}
