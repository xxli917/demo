package com.lixiaoxue.demolxx.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lixiaoxue.demolxx.R

class ScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)
        val v = """ |First Line |Second Line |Third Line """.trimMargin()
    }
}