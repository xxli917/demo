package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/12/15/1:58 PM
 */
public class FDDloaderTest {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = FDD.class.getClassLoader();
        System.out.println(loader);
        //一、 使用ClassLoader.loadClass()来加载类，不会执行初始化块
        loader.loadClass("com.nucarf.javalib.FDD");
        // 二、 使用Class.forName()来加载类，默认会执行初始化块
        Class.forName("com.nucarf.javalib.FDD");
        // 三、使用Class.forName()来加载类，指定ClassLoader，初始化时不执行静态块
        Class.forName("com.nucarf.javalib.FDD", false, loader);
    }

}


