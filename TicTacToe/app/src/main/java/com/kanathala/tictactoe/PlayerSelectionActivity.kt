package com.kanathala.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_player_selection.*

class PlayerSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_selection)

        buttonO.setOnClickListener {

            val intent = Intent(this@PlayerSelectionActivity,GameActivity::class.java)
            intent.putExtra("player",buttonO.text)
            intent.putExtra("ai",buttonX.text)
            startActivity(intent)
        }

        buttonX.setOnClickListener {
            val intent = Intent(this@PlayerSelectionActivity,GameActivity::class.java)
            intent.putExtra("player",buttonX.text)
            intent.putExtra("ai",buttonO.text)
            startActivity(intent)
        }
    }
}
