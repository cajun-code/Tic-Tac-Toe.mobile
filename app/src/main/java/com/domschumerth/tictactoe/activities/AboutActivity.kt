package com.domschumerth.tictactoe.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import com.domschumerth.tictactoe.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_about)

        val openSite = Intent()
        openSite.action = Intent.ACTION_VIEW

        button_github.setOnClickListener {
            openSite.data = Uri.parse("https://github.com/Domdabomb123")
            startActivity(openSite)
        }

        button_linkedin.setOnClickListener {
            openSite.data = Uri.parse("https://www.linkedin.com/in/dominic-schumerth-36658265/")
            startActivity(openSite)
        }
    }
}