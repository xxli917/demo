package com.nucarf.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class EmptyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empty)
       var view =  findViewById<View>(R.id.go)
        view.setOnClickListener {
            val entryClass = Class.forName("com.lixiaoxue.demolxx.TestActivity")
            var intent = Intent(this@EmptyActivity,entryClass)
            startActivity(intent)

        }


    }
}