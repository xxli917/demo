package com.nucarf.javalib;

/**
 * 稀疏数组
 *
 * author : lixiaoxue
 * date   : 2020/11/4/9:27 AM
 *
 *                  row col value
 *  0 0 0 0 0        5   5    2    （第一行表示数组5行，5列，有2个有效值）
 *  0 0 1 0 0        1   2    1     （下面都是有效值的坐标，value为具体数值）
 *  0 0 2 0 0        2   2    2
 *  0 0 0 0 0
 *  0 0 0 0 0
 *  时间换空间
 */
public class SparseArray {
    public static void main(String[] args){
        //生成二维数组
        int[][] array = new int[5][5];
        array[1][2] = 1;
        array[2][2] = 2;
        show(array);
        System.out.println("原数组转稀疏数组");
        int[][] sparseArray = arrayToSparseArray(array);
        show(sparseArray);

        array = sparseToArray(sparseArray);
        System.out.println("稀疏数组转原数组");

        show(array);


    }

    /**
     * 稀疏数组转原二维数组
     * 稀疏的（0，0）表示行数，（0，1）表示列数 （0，2）表示总有效数值
     * @param sparseArray
     * @return
     */
    private static int[][] sparseToArray(int[][] sparseArray) {
        if(sparseArray == null){
            return null;
        }
        int row = sparseArray[0][0];
        int col = sparseArray[0][1];
        int sum = sparseArray[0][2];
        int[][] array = new int[row][col];
        if(sum == 0){
            return array;
        }
        //根据值获取坐标赋值
        for(int i = 1;i<sparseArray.length;i++){
            int r = sparseArray[i][0];
            int c = sparseArray[i][1];
            int v = sparseArray[i][2];
            array[r][c] = v;

        }
        return array;
    }

    /**
     * 数组的列为 3 行数为总有效数+1 sum+1
     * @param array
     * @return
     */
    private static int[][] arrayToSparseArray(int[][] array) {
        if(array == null){
            return null;
        }
        int sum = 0;
        for(int i = 0;i<array.length;i++){
            for(int j = 0;j<array[i].length;j++){
                if(array[i][j]!=0){
                    sum++;
                }
            }
        }
        int[][] sparseArray = new int[sum+1][3];
        sparseArray[0][0]= 5;
        sparseArray[0][1]= 5;
        sparseArray[0][2] = sum;
        //第一行已经有值了，所以这里初始值为1
        int row = 1;
        //具体值赋值
        for(int i = 0;i<array.length;i++){
            for(int j = 0;j<array[i].length;j++){

                if(array[i][j] != 0){
                    sparseArray[row][0] = i;
                    sparseArray[row][1] = j;
                    sparseArray[row][2] = array[i][j];
                    row++;
                }
            }
        }
        return sparseArray;
    }

    public static void show(int[][] array){
        if(array == null){
            return;
        }
        for(int[] a: array){
            for(int v: a){
                System.out.print(v+"  ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
