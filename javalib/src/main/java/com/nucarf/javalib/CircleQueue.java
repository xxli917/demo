package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/4/11:01 AM
 */
public class CircleQueue {

    private int maxSize;
    private int front;//出队
    private int rear;//入队
    private int[] array;

    public CircleQueue(int maxSize) {
        this.maxSize = maxSize;
        array = new int[maxSize];
        front = 0;
        rear = 0;
    }

    //队列是否满 当入队指针和入队指针指向同一位置
    public boolean isFull() {
        //当前位置在加一个 等于出队位置，那么当前数据已经满了
        //当rear = 4时，array[4] 还没有赋值 设置为5 但是有效数据只有4，有一位无法赋值

        return (rear+1)%maxSize == front;
    }

    //队列是否为null
    public boolean isEmpty() {
        return front == rear;
    }

    public void addQueue(int value) {
        if (isFull()) {
            System.out.println("队列以满");
            return;
        }
        array[rear] = value;
        rear = (rear+1)%maxSize;
    }

    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为null");
        }
        int value = array[front];
        front = (front+1)%maxSize;
        return value;
    }

    //
    public int headData() {
        if (isEmpty()) {
            throw new RuntimeException("队列为null");
        }
        return array[front];
    }

    public void show() {
        if (isEmpty()) {
            System.out.println("队列为null");
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println("a[" + i + "]=" + array[i]);
        }
    }

    public static void main(String[] args) {
        CircleQueue queue = new CircleQueue(5);
        for (int i = 1; i < 5; i++) {
            queue.addQueue(i);
        }
        queue.show();
        System.out.println("头部数据 = " + queue.headData());

        System.out.println("取出数据 = "+queue.getQueue());
        System.out.println("取出数据 = "+queue.getQueue());

        queue.addQueue(10);
        queue.addQueue(20);
        queue.addQueue(30);


        System.out.println("头部数据 = "+queue.headData());
        queue.show();

    }


}
