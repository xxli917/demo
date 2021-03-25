package com.nucarf.javalib

import javax.jws.soap.SOAPBinding
import kotlin.math.truncate

/**
 *    author : lixiaoxue
 *    date   : 2020/11/6/9:50 AM
 */
fun main(args: Array<String>) {
    /*var a:Int = 1000
    var b:Int = a
    var c:Int = a
    var s1 = "test"
    var s2 = "test"
    var s3 = String(StringBuffer("ss"))
    var s4 = String(StringBuffer("ss"))
    var arr1:Array<Any> = arrayOf(1,2,3,4)
    var arr2:Array<Int> = arrayOf(1,2,3,4)
     var list:List<out Any> = ArrayList()



    // arr1 = arr2
    //高阶函数
    // {  }
    //s1.run {  }
    //内联类 包装基础类 编译期使用基本类型，减少堆创建对象存取对象堆消耗


    println(c==b)
    println(c===b)
    println(s1 == s2)
    println(s1 === s2)
    println(s1.equals(s2))
    println(s3.equals(s4))
    println(s3 == s4)
    println(s3 === s4)
    //kotlin中 equals 与==相同，比较的都是值
    //而===比较的才是地址
    //java中equals比较值， ==比较地址

    val sum: (Int, Int) -> Int = { x: Int, y: Int -> x + y }
   // sum(1,2)
    fun Example.ex(){
       println("扩展函数")
   }
    var ee = Example();
    ee.ex();*/

   /* var user = User("baibai")
    //返回的是自己
   var result =  user.apply {
        print("name $name age $age")
      // 1000
    }
    var result1 = user.run {
        print("name $name age $age")
         1000
    }

    println("apply result: $result")
    println("run result1: $result1")
    var test4 = Test4("baibai")
    var test5 = Test5()
    var person = Person("baibai",10)
    var user1 = User("bi")
    user1.age = 30;
*/

   /* var map = HashMap<String,String>()
    map.put("1","独家记忆")
    map.put("1","断点")
    map.forEach { (key, value) -> println("$key = $value") }*/
    /*var test1 = """line1
        |line2""".trimMargin()
    var test2 = "line1\nline2"
    print(test1)
    println("-------")
    print(test2)
    val text = """ |First Line 
    |Second Line |Third 
Line """.trimMargin()
    print(text)*/
    var example = Example()
    example.testString()


}
class Example {

    fun testString() {
        val str1 = "abc"
        val str2 = """line1\n
        line2
        line3
        """
        val js = """
        function myFunction()
        {
            document.getElementById("demo").innerHTML="My First JavaScript Function";
        }
        """.trimIndent()
        println(str1)
        println(str2)
        println(js)
    }
    lateinit var sss:String
    var s = null

    fun bar(a:()->Unit){

    }
    fun test(){
        bar { println("ssssss") }
    }

    fun String.testPerson():String{
        return this+"1111"
    }


    var p: String? ="--"
   // final var e = "";
    var n = p?:"11"
}


inline class C(val e:Demo){

}
public class Test(){
    inline fun sum(a: Int): Int {
        var sum = 8
        sum += a
        return sum
    }

    fun demo(){

        //val a = Demo.A
        var a:Int = 0;
        var b:Int = 2;
        a.equals(b)
        if(a ==b){

        }

    }
}
object Demo1 {
    const val A = 10

}
class Demo(){
    val str:String? = null
    fun text(){
        //str = "0";
        Test1.test()
        Test1.setA("a");
        var i:Int = 1;
        var j = 3;
        var k = i+j
        k = i.plus(j)
    }
    companion object{
        const val A = 10
    }

}
object Test1{
   private var a:String? = null;
    var l: String = ""
    fun test(){
    }
    fun setA(str:String){
        a = str
        var  retrofit = Retorfit.Build("name").build()

    }


}

 class Retorfit  private constructor(private val name: String) {

     class Build(var name: String) {
         fun build(): Retorfit {
             return Retorfit(name)
         }

     }
}
class Test5(name: String ){
    constructor() : this("") {

    }
}
class Test4( var name :String){
    //var c = name.




    val firstProperty = "First property: $name".also(::println)

    init {
        name = "hh"
        println("First initializer block that prints ${name}")
    }

    val secondProperty = "Second property: ${name.length}".also(::println)

    init {
        println("Second initializer block that prints ${name.length}")
    }
}
