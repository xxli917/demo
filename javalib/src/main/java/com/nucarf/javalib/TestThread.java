package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/12/15/10:51 AM
 */
public class TestThread {
    public static void demo() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
    }

    //方法锁 当前对象作为锁
    public synchronized void test1() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    public void test2() {
        synchronized (this) {
            int i = 5;

            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }

    }

    public void functionTest1() {
        final TestThread testThread = new TestThread();
        //final TestThread testThread1 = new TestThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                testThread.test1();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                testThread.test1();
            }
        }).start();
    }

    public static void codeTest1() {
        final TestThread testThread = new TestThread();
        final TestThread testThread1 = new TestThread();
        new Thread(new Runnable() {
            @Override
            public void run() {
                testThread.test2();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                testThread1.test2();
            }
        }).start();
    }

    public static void main(String[] args) {
        //相同对象，等第一个执行完在执行
        // codeTest1();
        demo();
    }
}