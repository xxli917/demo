package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/6/2:09 PM
 */
public class JosephDemo {
    public static void main(String[] args){
        //一共n个人，从第1个人开始，第3个人出
        int n = 6;
        int startNo = 1;
        int k = 3;
        joseph(n,startNo,k);

    }

    private static void joseph(int n, int startNo, int k) {
        CircleSingleLinkList linkList = new CircleSingleLinkList();
        for(int i = 1;i<=n;i++){
            linkList.add(i);
        }
        linkList.dis();
        CircleSingleLinkList.Node head = linkList.head;
        CircleSingleLinkList.Node preNode = head;
        //找到前一个
        while (preNode.next !=head){
            preNode = preNode.next;
        }
        System.out.println("前一个也就是倒数第一个数据 = "+preNode.data);

        //从startNo个开始，头和前一个向前移动 从第2个开始，也就是移动1次
        for(int i = 1;i<startNo;i++){
            preNode = preNode.next;
            head = head.next;
        }
        //循环出链表
        while (head.next != head){
            for(int i = 1;i<k;i++){
                head = head.next;
                preNode = preNode.next;
            }
            System.out.println("出队 = "+head.data+",  ");
            preNode.next =head.next;
            head = preNode.next;
            linkList.dis();
            System.out.println("-------");
        }
        System.out.println("最后一个出队 = "+head.data);

        //linkList.displayCircle();
    }

}
