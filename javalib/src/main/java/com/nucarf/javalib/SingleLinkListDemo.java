package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/3/3:32 PM
 * 单链表
 * 单链表的基本操作有：增（add)，删(remove)，改(set)，查（find)，插(insert)等。
 */
public class SingleLinkListDemo {

    public static void main(String[] args){
        SingleLinkList1 linkList = new SingleLinkList1();
        linkList.add(3);
        linkList.add(4);
        linkList.add(1);
        linkList.add(2);
        System.out.println("简单添加");
        linkList.display();
        linkList.clear();

        linkList.addByOrder(3);
        linkList.addByOrder(4);
        linkList.addByOrder(1);
        linkList.addByOrder(2);
        System.out.println("按顺序添加");
        linkList.display();
        System.out.println("是否存在元素4="+linkList.findData(4));
        System.out.println("是否存在元素10="+linkList.findData(10));
        System.out.println("删除元素4 =" +linkList.deleteByData(4));
        linkList.display();
        System.out.println("删除不存在元素10 =" +linkList.deleteByData(10));
        linkList.display();
        System.out.println("反转单链表");
        linkList.reverseTraversal();
        linkList.display();
        System.out.println("倒数第1个节点第值 = ");
        linkList.getIndex(1);









    }


}
class SingleLinkList1{

    public int size;//链表长度
    public Node head;
    public SingleLinkList1(){
        size = 0;
        //一个空链表头
        head = new Node(0);

    }
    public class Node{
        public int data;
        public Node next;
        public Node(int data){
            this.data = data;
        }

    }
    //添加元素
    public void add(int data){
        Node node = new Node(data);
        Node temp = head;
        while (temp.next != null){
            temp = temp.next;
        }
        //添加到队尾
        temp.next = node;
        size++;
    }
    //按顺序添加 正序
    public void addByOrder(int value){
        Node node = new Node(value);
        Node temp = head;

        while (temp.next != null){
            if(temp.next.data >node.data){
                //while循环停止，找到插入点
                break;
            }
            temp = temp.next;
        }
        node.next = temp.next;
        temp.next = node;
        size++;
    }



    //删除表头元素
    public void deleteHead(){
        if(head.next == null){
            return;
        }
        head.next = head.next.next;
        size--;
    }
    //查找元素
    public boolean findData(int data){
        if(head.next == null){
            return false;
        }
        Node temp = head.next;
        while (temp != null){
            if(temp.data == data){
                return true;
            }
            temp = temp.next;
        }
        return false;


    }
    public boolean deleteByData(int data){
        boolean flag = false;
        Node temp = head.next;
        while (temp.next != null){
            if(temp.next.data == data){
                //找到要删除元素 temp.next
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if(flag){
            temp.next = temp.next.next;
            size--;
            return true;
        }else {
            return false;
        }


    }


    //判断是否为空
    public boolean isEmpty(){
        //还可以根据head.next == null
        return (size==0);
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

    /**
     * 反转单链表
     * 头插法
     * 新建一个单链表 遍历旧链表，将元素一个个放到头节点后面
     * 最后将旧链表头指向新链表head.next =  newHead.next
     * newHead.next = temp  要迁移单节点
     * next = temp.next;
     * temp .next = newHead.next.next
     * temp.next要被赋予新值，我们还要遍历temp,所以新建一个保存temp.next
     * 最后将保存的值赋值给temp
     * temp =next
     *
     */

    public void reverseTraversal(){
        if(head.next == null){
            return;
        }
        Node newHead = new Node(0);
        Node next = null;
        Node temp = head.next;
        while (temp != null){
            next = temp.next;
            temp.next = newHead.next;
            newHead.next = temp;
            temp = next;
        }
        head.next = newHead.next;


    }
    public void clear(){
        head.next = null;
    }
    /**
     * 获取倒数第k个节点
     * 也就是正序 size-k+1 10-1+1 倒数第一个，正数第10个
     * 首先获取size
     */
    public void getIndex(int k){
        int size = getSize();
        if(k<=-1 || k>size){
            System.out.println("不存在此节点");
            return;
        }
        Node temp = head.next;

        int index = 1;
        while (temp != null){
            if(index == size-k+1){ //倒数第一个，正数第3个，包含第三个 3-1+1
                System.out.println("节点值为 = "+temp.data);
                break;
            }
            index++;
            temp = temp.next;
        }



    }

    private int getSize() {
        int size = 0;
        if(head.next == null){
            size =0;
        }
        Node temp = head.next;
        while (temp != null){
            size++;
            temp = temp.next;
        }
        return size;
    }

}


