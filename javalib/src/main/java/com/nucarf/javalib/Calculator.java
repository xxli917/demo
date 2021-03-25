package com.nucarf.javalib;

/**
 * author : lixiaoxue
 * date   : 2020/11/7/1:56 PM
 */
public class Calculator {
    public static void main(String[] args) {
        String input = "1*2+3+4-5";
        //
        Stack1 numStack = new Stack1(10);
        Stack1 intStack = new Stack1(10);
        int index = 0;
        while (true){
            char val= input.substring(index,index+1).charAt(0);
            //是否是操作符
            if(intStack.isOperator(val)){
                //
                if(intStack.isEmpty()){
                    intStack.push(val);
                }else{
                    if(intStack.isPriority(intStack.peek())>=intStack.isPriority(val)){
                            int num1 = numStack.pop();
                            int num2 = numStack.pop();
                           int result =  intStack.result(num1,num2,intStack.pop());
                           numStack.push(result);
                           intStack.push(val);
                    }else{
                        intStack.push(val);
                    }
                }

            }else{
                numStack.push(Integer.parseInt(String.valueOf(val)));
            }
            index++;
            if(index >input.length()-1){
                break;
            }
        }

        //出栈
        while (!numStack.isEmpty()){
                int num1 = numStack.pop();
                if(!numStack.isEmpty()){
                    int num2 = numStack.pop();
                    int val =  intStack.pop();
                    int result =  intStack.result(num1,num2,val);
                    numStack.push(result);
                }else{
                    System.out.println("结果 = "+num1);
                    break;
                }
        }


    }
}
class Stack1{
    private int maxSize;
    private int[] array;
    private int top = -1;
    public Stack1(int maxSize){
        this.maxSize = maxSize;
        array = new int[maxSize];
    }
    public int peek(){
        if(isEmpty()){
            return -1;
        }
        return array[top];
    }
    public void push(int c){
        if(isFull()){
            System.out.println("栈以满");
            return;
        }
        top++;
        array[top] = c;


    }
    public int result(int num1,int num2,int c){
        if(c == '+'){
            return num1+num2;
        }else if(c == '-'){
            return num2 - num1;
        }else if(c == '*'){
            return  num1 *num2;
        }else if (c == '/'){
            return num2/num1;
        }
        return -1;
    }

    public boolean isOperator(int c){
        return c == '+' || c == '-' ||c=='*'|| c=='/';
    }
    public int isPriority(int c){
        if(c=='+'|| c=='-' ){
            return 0;
        }else if(c == '*' || c == '/'){
            return 1;
        }
        return -1;

    }

    public int pop() {
        if(isEmpty()){
            return '0';
        }
        int a = array[top];
        top--;
        return a;
    }
    public boolean isEmpty(){
        return top<0;
    }
    public boolean isFull(){
        return top == maxSize-1;
    }

}
