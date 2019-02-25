package com.lasley.adam.codingtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentManager

abstract class MainActivity : AppCompatActivity() {

    abstract var fragManager: FragmentManager
    val delayTransitionHandler: Handler = Handler()
    private val mRunnable = Runnable { }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragManager = supportFragmentManager
        delayTransitionHandler.postDelayed(mRunnable, 1000)
    }

    private fun loadInitialFragment() {
    }

}
