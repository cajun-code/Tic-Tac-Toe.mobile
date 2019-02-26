package com.lasley.adam.codingtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chibatching.kotpref.Kotpref
import com.lasley.adam.codingtest.Fragments.MainMenu

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Kotpref.init(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainMenu()).commit()
    }
}
