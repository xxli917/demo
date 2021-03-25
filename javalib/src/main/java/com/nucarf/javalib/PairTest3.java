package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/12/3:59 PM
 */
public class PairTest3 {
    public static void main(String[] args) {
        Manager ceo = new Manager("Gus Greedy", 8000, 2020, 12, 15);
        Manager cfo = new Manager("sid sneaky", 6000, 2020, 12, 15);
        Pair<Manager> buddies = new Pair<>(ceo, cfo);
        printBuddies(buddies);
        ceo.setBonus(100000);
        cfo.setBonus(590000);
        Manager[] managers = {ceo, cfo};
        Pair<Employee> result = new Pair<>();
        minmaxBonus(managers, result);
        System.out.println("first:" + result.getFirst().getName() + "second: " + result.getSecond().getName());
        maxminBonus(managers, result);
        System.out.println("first:" + result.getFirst().getName() + "second: " + result.getSecond().getName());

        String s = "1111";
        String s2 = s;
        System.out.println(s + "---" + s2); //111

        s = "3333";
        System.out.println(s + "---" + s2); //111

        //test(s1);
        // System.out.println(s1); //111
        Test8 test8 = new Test8();

        StringBuffer a = new StringBuffer("A");
        StringBuffer b = new StringBuffer("B");
        String a1 = "A";
        String b1 = "B";
        operate1(a1, b1);
        System.out.println(a1 + "." + b1);
    }

    static void operate(StringBuffer x, StringBuffer y) {
        x.append(y);
        y = x;
    }
    static void operate1(String x, String y) {
        x= x+y;
        y = x;
    }


    private static void test(String s) {
        s = new String("2222");
        System.out.println(s);
    }

    private static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
        if (a == null || a.length == 0) return;
        Manager min = a[0];
        Manager max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.getBonus() > a[i].getBonus()) min = a[i];
            if (max.getBonus() < a[i].getBonus()) max = a[i];
        }
        result.setFirst(max);
        result.setSecond(min);
    }

    //<s super Manager> 可以set
    private static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
        if (a == null || a.length == 0) return;
        Manager min = a[0];
        Manager max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (min.getBonus() > a[i].getBonus()) min = a[i];
            if (max.getBonus() < a[i].getBonus()) max = a[i];
        }
        result.setFirst(min);
        result.setSecond(max);
    }

    private static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee scond = p.getSecond();
        System.out.println("first:" + first.getName() + "and: " + scond.getName());
    }
}

class Test8 {
    static {
        System.out.println("------static-----");
    }

    public Test8() {
        System.out.println("------constroc-----");

    }
}


