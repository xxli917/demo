package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/3/3:32 PM
 * 单链表
 * 单链表的基本操作有：增（add)，删(remove)，改(set)，查（find)，插(insert)等。
 */
public class SingleLinkList {
    public int size;//链表长度
    public Node head;
    public SingleLinkList(){
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
            size++;
            return;
        }else{
            Node temp = head;
            while (true){
                if(temp.next == null){
                    break;
                }
                temp = temp.next;

            }
            //新建的下一个节点指向当先head
            temp.next = node;
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
    public static void main(String[] args){
        SingleLinkList singleLinkList = new SingleLinkList();
        for(int i = 0;i<10;i++){
            singleLinkList.add(i);
        }
        singleLinkList.display();
        singleLinkList.deleteHead(); //头始终指向第一位
        //singleLinkList.deleteByData(9);
        singleLinkList.display();

    }

}


