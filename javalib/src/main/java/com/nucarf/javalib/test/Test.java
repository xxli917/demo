package com.nucarf.javalib.test;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashSet;

/**
 * author : lixiaoxue
 * date   : 2020/12/14/9:38 AM
 */
public class Test {
    public static ReferenceQueue<User> queue = new ReferenceQueue<User>();

    public static void checkQueue() {
        if (queue != null) {
            @SuppressWarnings("unchecked")
            Reference<User> ref = (Reference<User>) queue.poll();
            if (ref != null)
                System.out.println(ref + "......" + ref.get());
        }
    }

    public static void main(String[] args) {
        HashSet<SoftReference<User>> hs1 = new HashSet<SoftReference<User>>();
        HashSet<WeakReference<User>> hs2 = new HashSet<WeakReference<User>>();
        HashSet<PhantomReference<User>> hs3 = new HashSet<PhantomReference<User>>();
        for(int i = 0;i<10000;i++){
            User user = new User("soft" + i);
            SoftReference<User> soft = new SoftReference<User>(user, queue);
            user = null;
            //user被垃圾回收了，就清除本省

        }

        //创建 10 个软引用
        for (int i = 1; i <= 10; i++) {
            SoftReference<User> soft = new SoftReference<User>(new User("soft" + i), queue);
            System.out.println("create soft" + soft.get());
            hs1.add(soft);
        }
        System.gc();
        checkQueue();

        //创建 10 个弱引用
        for (int i = 1; i <= 10; i++) {
            WeakReference<User> weak = new WeakReference<>(new User("weak" + i), queue);
            System.out.println("create weak" + weak.get());
            hs2.add(weak);
        }
        System.gc();
        checkQueue();




        for(int i = 1;i<10;i++){
           PhantomReference phantom = new  PhantomReference<User> (new User("phantom"+i),queue);
            System.out.println("create phantom "+phantom.get());
                    hs3.add(phantom);
        }
        System.gc();
        checkQueue();
    }
    //ThreadLocal local = new ThreadLocal();


}

