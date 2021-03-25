package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/5/10:54 AM
 * 双向链表
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args){
        DoubleLinkedList linkedList =new DoubleLinkedList();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);
        linkedList.display();




    }
}
class DoubleLinkedList{
    private int value;
    private Node head = new Node(-1);
    private Node tail = new Node(-1);
    public DoubleLinkedList(){

    }

    class Node{
        private int data;
        private Node next;
        private Node pre;
        public Node(int data){
            this.data = data;
        }
    }
    //增
    public void add(int data){
        Node node = new Node(data);
        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        temp.next = node;
        node.pre = temp;
        tail.pre = temp;
       // node.next = tail;
    }
    //正向遍历
    public void display(){
        if(head.next == null){
            return;
        }
        Node temp = head.next;
        while (temp != null){
            System.out.println("arr="+temp.data);
            temp = temp.next;
        }
    }

}
