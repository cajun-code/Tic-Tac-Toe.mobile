package com.domschumerth.tictactoe.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.domschumerth.tictactoe.states.CellState
import com.domschumerth.tictactoe.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, BoardActivity::class.java)

        button_x.setOnClickListener {
            intent.putExtra("side", CellState.X.ordinal)
            startActivity(intent)
        }

        button_o.setOnClickListener {
            intent.putExtra("side", CellState.O.ordinal)
            startActivity(intent)
        }
    }
}
