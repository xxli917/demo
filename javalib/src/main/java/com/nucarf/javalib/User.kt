package com.nucarf.javalib

/**
 * author : lixiaoxue
 * date   : 2020/11/11/10:14 AM
 */
data class User(var name:String){
     var age:Int=0

}
data  class  Test6(val value:  Int) {

    val safeValue:  Int
        get() =  if (value <  0) 0  else value

}
