package com.lixiaoxue.demolxx.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lixiaoxue.demolxx.R

class ContentProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
        classLoader
    }
}