package com.nucarf.javalib;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.crypto.Data;

/**
 * author : lixiaoxue
 * date   : 2020/11/12/3:46 PM
 */
public class Employee {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getHireDay() {
        return hireDay;
    }

    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }

    private double salary;
    private int year;
    private int month;
    private int day;
    private Date hireDay;
    public Employee(String n,double s,int year,int month,int day){
        name = n;
        salary = s;
        GregorianCalendar calendar =new GregorianCalendar(year,month - 1,day);
        hireDay = calendar.getTime();

    }

    public void raiseSalary(double byPercent){
        double raise = salary * byPercent/100;
        salary += raise;
    }
}
class Manager extends Employee{

    public Manager(String n, double s, int year, int month, int day) {
        super(n, s, year, month, day);
        bonus = 0;
    }
    public double getSalary(){
        double baseSalary = super.getSalary();
        return baseSalary +bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    private double bonus;
}
class Pair<T>{
    private T first;

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    private T second;
    public Pair(){

    }
    public Pair(T first,T second){
        this.first = first;
        this.second = second;
    }
    public T getFirst(){
        return first;
    }

}
class PairAlg{
    public  static boolean hasNulls(Pair<?> p){
        return (p.getFirst() == null || p.getSecond() == null);
    }
    public static void swap(Pair<?> p){
        swapHelper(p);
    }
    public static <T> void swapHelper(Pair<T> p){
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);

    }
}

