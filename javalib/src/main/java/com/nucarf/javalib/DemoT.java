package com.nucarf.javalib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * author : lixiaoxue
 * date   : 2020/11/9/10:27 AM
 */
public class DemoT {
    public static void main(String[] args) {
        String s1 = "1";
        String s2 = "1";
        String s3 = new String("2");
        String s4 = new String("2");
        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        System.out.println(s3 == s4);
        System.out.println(s3.equals(s4));
        Object[] arr1;
        String[] arr2 = {"1", "2", "3", "4", "5"};
        arr1 = arr2;
        arr1[1] = 0;
        String a = arr2[1];
        System.out.println(a);
        List<Object> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        // list1 = list2;
        //通配符让数组是协变的,不能add,只能读取对象单称为生产者
        List<? extends Object> list3 = new ArrayList<>();
        // var list:List<out Any> = ArrayList()
        //可以添加，不能获取，java单继承，也就是一种，所以可以添加，而获取不知道拿什么类型接受，所以只能add
        //写入的称为消费者
        List<? super String> list4 = new ArrayList<>();
        //添加只能是String,拿到Object
        //list4.add(new Object());
        //  Object l  = list4.get(0);


        list3 = list2;
        list2.add("1");
        //list3.get(0);

        Retorfit retorfit = new Retorfit.Build("test").build();
        HashMap<String,String> map = new HashMap<>();
        Set<Map.Entry<String,String>> s = map.entrySet();
        Iterator<Map.Entry<String,String>> iterator1  = s.iterator();

        while (iterator1.hasNext()){
            Map.Entry<String,String> entry = iterator1.next();
            entry.getKey();
            entry.getValue();

        }
       Set<String> set =  map.keySet();
       Iterator<String> iterable = set.iterator();
       while (iterable.hasNext()){

           iterable.next();
       }
    }
}

class Retorfit1 {
    private String name;
    protected Retorfit1(String name){
        this.name = name;
    }

    public static class Build {
        String name;
        public Build(String name) {
          this.name = name;
        }

        public Retorfit1 build() {
            return new Retorfit1(name);
        }

    }

}
class Person1<T>{
    T s;
    public void set(T s){
        this.s = s;

    }
    public T get(){
        return s;
    }

}
class Student{
    private Student s;
    public void printAge(){
        System.out.println("student= age");
    }


}

class S extends Student {

}
class Test10{
    public static void main(String[] args){
        Person1<S> s = new Person1<>();
        prinltn(s);
    }

    public static void prinltn(Person1<? extends Student> per){
       // per = new Person1<>();
        Person1<S> s = new Person1<>();
        per = s;
        Student student = per.get();
        //set(? extends Student student)


       // per.set(per); //编译器只知道某个Student的子类型，不确定是哪个，所以拒绝加入任何一个

        Person1<?> p2= new Person1<>();
       // p2.set(student); //set(?)确定不了类型，所以不可以
        Object o = p2.get();


    }
    public boolean hasNull(Person1<?> person1){
        return person1 == null;
    }
    public <T> boolean hasNull1(Person1<T> person1){
        return person1 == null;
    }

}
