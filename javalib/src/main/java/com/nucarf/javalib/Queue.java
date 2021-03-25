package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/3/4:38 PM
 * 先进先出 first in first out
 * 队列只允许在一端插入，在另一端删除
 * 数组实现队列  第一个在0 第二个在1 取得时候也先取1
 *
 * 一次性队列，假满，不能循环使用
 * 要想循环需要根据% 看CircleQueue
 */
public class Queue {
    private int maxSize;
    private int front;//出队
    private int rear;//入队
    private int[] array;
    public Queue(int maxSize){
        this.maxSize = maxSize;
        array = new int[maxSize];
        front = -1;
        rear = -1;
    }
    //队列是否满
    public boolean isFull(){
        return (rear == maxSize-1);
    }
    //队列是否为null
    public boolean isEmpty(){
        return front==rear;
    }

    public void addQueue(int value){
        if(isFull()){
            System.out.println("队列以满");
            return;
        }
        rear++;
        array[rear] = value;
    }

    public int getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列为null");
        }
        front++;
        return array[front];
    }
    //
    public int headData(){
        if(isEmpty()){
            throw new RuntimeException("队列为null");
        }
        return array[front+1];
    }
    public  void show(){
        if(isEmpty()){
            System.out.println("队列为null");
        }
        for(int i = 0;i<array.length;i++){
            System.out.println("a["+i+"]="+array[i]);
        }
    }

    public static void main(String[] args){
        Queue queue = new Queue(5);
        for(int i = 0;i<6;i++){
            queue.addQueue(i);
        }
        queue.show();
        System.out.println("头部数据 = "+queue.headData());

        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());
        System.out.println(queue.getQueue());

        //System.out.println("头部数据 = "+queue.headData());
    }





}
