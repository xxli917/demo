package com.lixiaoxue.demolxx.bean

/**
 *    author : lixiaoxue
 *    date   : 2020/11/23/10:30 AM
 */
data class S(val name:String,val age:Int,val value: Int){
    val names
       get() = name+"m"
    val safeValue:  Int
        get() =  if (value <  0) 0  else value
    val a :Any = ""

}