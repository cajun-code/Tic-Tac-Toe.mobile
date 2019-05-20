package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val player = intent.getCharExtra("player", ' ')
        Log.d(TAG, "Player is: '$player'")
        val game = Game(player)
        Log.d(TAG, "Player enum is: ${game.playerPiece}")
    }

    companion object {
        const val TAG = "GameActivity"
    }
}
