package com.nucarf.javalib;

import java.io.FileInputStream;

/**
 * author : lixiaoxue
 * date   : 2020/11/5/1:38 PM
 */
public class SortDemo {
    public static void main(String[] args) {
        int[] a = {2, 5, 10, 1, 0, 8, 9, 7, 6, 4, 3};
        /**
         *
         */
        //selectionSort(a);
       // sort3(a);
       // binarySort(a);
       // show(a);
        double b = 7.8;
        double c = 7.1;
        int d = (int) b;
        int e = (int)c;
        System.out.print("d = "+d+"---"+"e="+e);

    }


    //选择排序
    //假设第一位为最小值，拿后面的所有值和他比较，如果比他小则对换
    // 一轮比完之后，第一个定位了，继续比后面的
    private static void sort(int[] a) {
        if (a == null) {
            return;
        }
        for (int i = 0; i < a.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                //和最小的比较
                if (a[j] < a[minIndex]) {
                    //和当前最小值比较
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = temp;
            }
        }
    }

    //冒泡排序
    //比较相邻两个数，如果大于就上移动
    //那下次循环的的最高值就改变了 j<a.length-i-1
    public static void sort1(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    //直接插入排序
    public static void sort2(int a[]) {
        if (a == null) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0; j--) {
                if (a[j] < a[j - 1]) {
                    /*int temp = a[j-1];
                    a[j-1] = a[j];
                    a[j] = temp;*/
                    swap(a, j, j - 1);
                } else {
                    break;//因为前面都是有序的，则不用比较了}

                }
            }

        }
    }

    public static void swap(int a[], int j, int i) {
        int temp = a[j];
        a[j] = a[i];
        a[i] = temp;

    }
    //二分插入排序
    public static void sort3(int a[]) {
        if (a == null) {
            return;
        }
        int mid,low,high;
        for (int i = 1; i < a.length; i++) {
            low = 0;
             high = i-1;
            int temp = a[i];

            while (low<=high){
                //加法
                mid = (high+low)/2;
                if (temp<a[mid] ) {
                    // 插入点在低半区
                    high = mid - 1;
                } else {
                    // 插入点在高半区
                    low = mid + 1;
                }
            }
            //low为最终值所在位置
            //将所以值向后移
                for (int j = i-1 ; j >=low; j--) {
                    a[j + 1] = a[j];
                }

            //将待插入记录回填到正确位置.
            a[low] = temp;

        }
    }

    public static void binarySort(int[] source) {
        int i, j;
        int high, low, mid;
        int temp;
        for (i = 1; i < source.length; i++) {
            // 查找区上界
            low = 0;
            // 查找区下界
            high = i - 1;
            //将当前待插入记录保存在临时变量中
            temp = source[i];
            while (low <= high) {
                // 找出中间值
                 mid = (low + high) / 2;
                //mid = (low + high) >> 1;
                //如果待插入记录比中间记录小
                if (temp<source[mid] ) {
                    // 插入点在低半区
                    high = mid - 1;
                } else {
                    // 插入点在高半区
                    low = mid + 1;
                }
            }
            //将前面所有大于当前待插入记录的记录后移
            for (j = i - 1; j >=low; j--) {
                source[j + 1] = source[j];
            }
            //将待插入记录回填到正确位置.
            source[low] = temp;
           // System.out.print("第" + i + "趟排序：");
          //  printArray(source);
        }
    }

    public static void selectionSort(int[] arr) {

        for (int i = 0; i < arr.length; i++) {

            //最小数的索引
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {

                //找到最小数，并记录最小数的索引
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            //交换符合条件的数
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }

    }

    private static void show(int[] a) {

       if (a == null) {
            return;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + "--");
        }

    }
}


