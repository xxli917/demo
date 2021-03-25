package com.lixiaoxue.demolxx.java;

import java.util.ArrayList;
import java.util.List;

/**
 * author : lixiaoxue
 * date   : 2020/11/3/9:31 AM
 */
public class BinTree {
    public BinTree left;
    public BinTree right;
    public BinTree root;
    public Object data;
    public List<BinTree> datas;
    public BinTree(BinTree left, BinTree right, Object data){
        this.left=left;
        this.right=right;
        this.data=data;
    }
    //    将初始的左右孩子值为空
    public BinTree(Object data){
        this(null,null,data);
    }

    public BinTree() {

    }

    public void create(Object[] objs){
        datas=new ArrayList<BinTree>();
        //        将一个数组的值依次转换为Node节点
        for(Object o:objs){
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
        }
    }
    //    后序遍历
    public void afterorder(BinTree root){
        if(root!=null){
            System.out.println(root.data);
            afterorder(root.left);
            afterorder(root.right);
        }
    }
    public static void main(String[] args) {
        BinTree bintree = new BinTree();
        Object[] a = {2, 4, 5, 7, 1, 6, 12, 32, 51, 22};
        bintree.create(a);
        bintree.preorder(bintree.root);
    }

    }
