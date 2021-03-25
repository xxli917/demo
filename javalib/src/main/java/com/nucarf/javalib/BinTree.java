package com.nucarf.javalib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;


/**
 * author : lixiaoxue
 * date   : 2020/11/3/9:31 AM
 */
public class BinTree {
    public BinTree left;
    public BinTree right;
    public BinTree root;
    public int data;
    public List<BinTree> datas;
    public BinTree(BinTree left, BinTree right, int data){
        this.left=left;
        this.right=right;
        this.data=data;
    }
    //    将初始的左右孩子值为空
    public BinTree(int data){
        this(null,null,data);
    }

    public BinTree() {

    }

    public void create(int[] objs){
        datas=new ArrayList<BinTree>();
        //        将一个数组的值依次转换为Node节点
        for(int o:objs){
            datas.add(new BinTree(o));
        }
//        第一个数为根节点
        root=datas.get(0);
//        建立二叉树
        for (int i = 0; i <objs.length/2; i++) {
//            左孩子
            datas.get(i).left=datas.get(i*2+1);
//            右孩子
            if(i*2+2<datas.size()){//避免偶数的时候 下标越界
                datas.get(i).right=datas.get(i*2+2);
            }
        }
    }
    //先序遍历
    public void preorder(BinTree root){
        if(root!=null){
            System.out.println(root.data);
            preorder(root.left);
            preorder(root.right);
        }
    }
    //中序遍历
    public void inorder(BinTree root){
        if(root!=null){
            inorder(root.left);
            System.out.println(root.data);
            inorder(root.right);
            /**
             *
             *
             * inorder(null)
             * inorder(8)   sys 8  inorder(null)
             *                     inorder(null)
             * inorder(4)   sys 4  inorder(9) sys 9
             * inorder(2)
             * inorder(1)
             */
        }
    }
    //    后序遍历
    public void afterorder(BinTree root){
        if(root!=null){
            afterorder(root.left);
            afterorder(root.right);
            System.out.println(root.data);

        }
    }
    public static void main(String[] args) {
        BinTree bintree = new BinTree(0);
        int[] a = {1,2, 3, 4, 5, 6, 7, 8, 9, 10};
       // bintree.create(a);
        bintree.createOrder(a);
        bintree.preorder(bintree);
        //bintree.afterorder(bintree);
        //bintree.inorder(bintree);
        //sum1();

        System.out.println("树高="+bintree.getHeight());
    }

    private void createOrder(int[] a) {
        if(a == null){
            return;
        }

        for(int i = 0;i<a.length;i++){
            BinTree binTree = new BinTree(a[i]);
            add(binTree);
        }
       /* BinTree bt = new BinTree(1);
        bt.add(new BinTree(3));
        bt.add(new BinTree(5));
        bt.add(new BinTree(7));
        bt.add(new BinTree(9));*/
    }

    //有序： 左子树小于树跟，树根小于右子树
    public void add(BinTree x) {
        //如果添加的树的值小于树根的值，加到左子树
        if (x.data < data) {
            /**
             *       0
             *         1
             *           2
             *             3
             *               4
             *
             */
            //若左子树为空，则加上
            if (left == null) {
                left = x;
            }
            //左子树不空，则将左子树作为树根，继续添加
            else {
                left.add(x);
            }
        } else {
            if (right == null) {
                right = x;
            } else {
                right.add(x);
            }
        }
    }

        //1+2
    static int i = 1;
    public static void sum(int n){
        n=n+i; //业务代码1
        //递归头
        if(i==100){
            System.out.println(n);
            return;
        }
        i++;   //业务代码2
        sum(n); //递归体


    }
    public static void sum1(){
        int sum = 0;
        for(int i = 0;i<=100;i++){
            sum  = sum+i;
        }
        System.out.println(sum);


    }
    //suei
    public static int sum3(int num){
        if(num == 4){
            return 4;
        }
        return num + sum3(num+1);
        /**
         * 3+4
         * 2+sum(3) 2+3+4
         *1+sum(2) 1+2+3+4
         * 0+sum(1)
         */

    }
    //计算树高
    public int getHeight() {
        int hl, hr;
        if (left == null)
            hl = 0;
        else
            hl = left.getHeight();
        if (right == null)
            hr = 0;
        else
            hr = right.getHeight();
        return Math.max(hl, hr) + 1;
    }




}
