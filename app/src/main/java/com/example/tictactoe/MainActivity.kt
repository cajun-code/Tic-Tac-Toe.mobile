package com.example.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oButton.setOnClickListener {
            nextActivtiy('O')
        }

        xButton.setOnClickListener {
            nextActivtiy('X')
        }

    }

    fun nextActivtiy(piece: Char) {
        val player = piece
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("player", player)
        startActivity(intent)

    }
}
