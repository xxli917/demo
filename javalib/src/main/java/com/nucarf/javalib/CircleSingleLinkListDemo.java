package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/3/3:32 PM
 * 单循环链表
 * 单链表的基本操作有：增（add)，删(remove)，改(set)，查（find)，插(insert)等。
 */
public class CircleSingleLinkListDemo {
    public static void main(String[] args){
       /* CircleSingleLinkList singleLinkList = new CircleSingleLinkList();
        for(int i = 0;i<10;i++){
            singleLinkList.add(i);
        }
        singleLinkList.display();
        singleLinkList.deleteHead(); //头始终指向第一位
        //singleLinkList.deleteByData(9);
        singleLinkList.display();*/
        //一共有n个人，从k开始报数，报到m的人出列，谁是最后出列？
        int n= 41;
        int k = 0;
        int m= 3;

        CircleSingleLinkList singleLinkList = new CircleSingleLinkList();
        for(int i = 0;i<n;i++){
            singleLinkList.add(i+1);
        }
        // 定义计数器
        int count = 0;
        //定义出列次序
        int order =1;
        CircleSingleLinkList.Node current = singleLinkList.head;
        CircleSingleLinkList.Node prev = current;
        // 一旦收尾相接，当前节点指向了自身，证明队列中只有一个人员了，跳出循环，游戏结束
        while (current.next != current) {
            count ++;
            if(order == 1){
                if (count == k+m) {//0+3
                    prev.next = current.next;
                    count = 0;
                    System.out.printf("第%2d个人出列！是第%2d个出列的",current.data,order);
                    System.out.println();
                    order++;
                }
            }else{
                if (count == m) {
                    prev.next = current.next;
                    count = 0;
                    System.out.printf("第%2d个人出列！是第%2d个出列的",current.data,order);
                    System.out.println();

                    order++;
                }
            }

            prev = current;
            current = current.next;

        }
        System.out.printf("第%2d个人出列！是第%2d个出列的",current.data,order);
    }


}
class CircleSingleLinkList{
    public int size;//链表长度
    public Node head;
    public CircleSingleLinkList(){
        size = 0;
        head = null;

    }
    public class Node{
        public Object data;
        public Node next;
        public Node(Object data){
            this.data = data;
        }

    }
    //在表头添加元素
    public void add(Object data){
        Node node = new Node(data);
        if(head == null){
            head = node;
            head.next = head;
            size++;
            return;
        }else{
            Node temp = head;

            for(int i = 1;i<size;i++){
                temp = temp.next;
            }

            //新建的下一个节点指向当先head
            temp.next = node;
            //循环单链表
            node.next = head;
            size++;
            //新添加的作为表头
            // head = node;
        }

    }
    //删除表头元素
    public void deleteHead(){
        if(size == 0){
            return;
        }
        head = head.next;
        size--;
    }
    //查找元素
    public boolean findData(Object data){
        Node current = head;
        while (current != null){
            if(current.data == data){
                return true;
            }
            current = current.next;
        }
        return false;

    }
    public boolean deleteByData(Object data){
        Node current = head;
        Node pre = head;
        while (current != null){
            if(current.data == data){
                if(current==pre){
                    //删除header 9-8-7 只需要把header置为8
                    head = current.next;
                }else{
                    //9--8--7 删除8  9-7

                    pre.next = current.next;
                }
                size--;

                return true;
            }
            pre = current;
            current = current.next;

        }
        return false;
    }

    //删除指定元素
    public boolean delete(Object value){
        if(size==0){
            return false;
        }
        Node current = head;
        Node pre = head;
        while (current.data!=value){
            if(current.next==null){
                return  false;
            }else {
                pre = current;
                current = current.next;
            }
        }
        if(current == head){
            head = current.next;
            size--;
        }else {
            pre.next = current.next;
            size--;
        }
        return true;
    }
    //判断是否为空
    public boolean isEmpty(){
        return (size==0);
    }
    public void dis(){
        Node node = head;
        while (node.next != head){
            System.out.print(node.data+",");
            node = node.next;
        }
        System.out.print(node.data);

    }
    //遍历链表
    public void display(){
        if(size>0){
            Node node = head;
            while (true){
                System.out.print(node.data+"--");
                if(node.next == null){
                    //跳出一层循环
                    break;
                }
                node = node.next;
            }

            System.out.println();
        }else{
            System.out.println("[]");
        }
    }
    public Node getNode(int i){
        Node renode=head;
        for(int j=-2;j<i;j++){
            renode=renode.next;
        }
        return renode;
    }
    /**
     * 于是决定了一个自杀方式
     * 41个人排成一个圆圈，由第1个人开始报数，每报数到第3人该人就必须自杀，然后再由下一个重新报数，直到所有人都自杀身亡为止。
     * 然而Josephus 和他的朋友并不想遵从。
     * 首先从一个人开始，越过k-2个人（因为第一个人已经被越过），
     * 并杀掉第k个人。
     * 接着，再越过k-1个人，并杀掉第k个人。
     * 这个过程沿着圆圈一直进行，直到最终只剩下一个人留下，这个人就可以继续活着。
     * 问题是，给定了和，一开始要站在什么地方才能避免被处决？
     * Josephus要他的朋友先假装遵从，他将朋友与自己安排在第16个与第31个位置，于是逃过了这场死亡游戏。
     * 1 2 3 4 5 6 7 8 9
     * 1 2 4 5 6 7 8 9
     * 1 2 4 5 7 8 9
     * 1 2 4 5 7 8
     * 1 2 5 7 8
     * 1 2 5 7
     * 1 2 7
     * 1 7
     */


}


