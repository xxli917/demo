package com.lixiaoxue.demolxx.android

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.transition.*
import com.lixiaoxue.demolxx.R

class TranstionActivity : AppCompatActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transtion)
        Transition
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView = LinearLayout(this)
        rootView.orientation = LinearLayout.VERTICAL
        setContentView(rootView)
        val viewGroup1 = getTemplate("无动画", Runnable {

        })
        val viewGroup2 = getTemplate("默认动画", Runnable {
            TransitionManager.beginDelayedTransition(rootView)
        })
        val viewGroup3 = getTemplate("淡入淡出", Runnable {
           // TransitionManager.go()
            TransitionManager.beginDelayedTransition(rootView, Fade().setDuration(1000))
        })
        val viewGroup4 = getTemplate("滑动动画", Runnable {
            TransitionManager.beginDelayedTransition(rootView, Slide().setDuration(1000))
        })
        val viewGroup5 = getTemplate("滑动动画 - 右边", Runnable {
            TransitionManager.beginDelayedTransition(rootView, Slide(Gravity.RIGHT).setDuration(1000))
        })
        rootView.addView(viewGroup1)
        rootView.addView(viewGroup2)
        rootView.addView(viewGroup3)
        rootView.addView(viewGroup4)
        rootView.addView(viewGroup5)
    }

    private fun getTemplate(text: String, runnable: Runnable): ViewGroup {
        val viewGroup = LayoutInflater.from(this).inflate(R.layout.view_transition, null) as ViewGroup
        viewGroup.findViewById<Button>(R.id.target).text = text
        viewGroup.findViewById<Button>(R.id.button1).setOnClickListener {
            runnable.run()
            viewGroup.findViewById<Button>(R.id.target).visibility = View.INVISIBLE
        }
        viewGroup.findViewById<Button>(R.id.button2).setOnClickListener {
            runnable.run()
            viewGroup.findViewById<Button>(R.id.target).visibility = View.VISIBLE
        }
        viewGroup.findViewById<Button>(R.id.button3).setOnClickListener {
            runnable.run()
            val layoutParams = viewGroup.findViewById<Button>(R.id.target).layoutParams as LinearLayout.LayoutParams
            layoutParams.width = 300
            layoutParams.height = 200
            layoutParams.topMargin = 100
            viewGroup.findViewById<Button>(R.id.target).layoutParams = layoutParams
        }
        viewGroup.findViewById<Button>(R.id.button4).setOnClickListener {
            runnable.run()
            val layoutParams =  viewGroup.findViewById<Button>(R.id.target).layoutParams as LinearLayout.LayoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            layoutParams.topMargin = 0
            viewGroup.findViewById<Button>(R.id.target).layoutParams = layoutParams
        }
        return viewGroup
    }
}

