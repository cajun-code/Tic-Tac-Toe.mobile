package com.example.tictactoe.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.example.tictactoe.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class GameOverDialogHelper(private val context: Context) : BaseDialogHelper() {
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.game_over_dialog, null)

    }
    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)

    private val tvOutcome: AppCompatTextView by lazy {
        dialogView.findViewById<AppCompatTextView>(R.id.tvOutcome)
    }

    // start game button
    private val playAgain: MaterialButton by lazy {
        dialogView.findViewById<MaterialButton>(R.id.btnPlayAgain)
    }

    // quit game button
    private val quitGame: MaterialButton by lazy {
        dialogView.findViewById<MaterialButton>(R.id.btnQuit)
    }

    // playAgainClickListener with listener
    fun playAgainClickListener(func: (() -> Unit)? = null) =
        with(playAgain) {
            setClickListenerToDialogIcon(func, null)
        }

    fun quitGameClickListener(func: (() -> Unit)? = null) =
        with(quitGame) {
            setClickListenerToDialogIcon(func, null)
        }

    // view click listener as extension function
    private fun View.setClickListenerToDialogIcon(func: (() -> Unit)?, card: MaterialCardView?) =
        setOnClickListener {
            func?.invoke()
            dialog?.dismiss()
        }

    fun setHeaderText(msg: String) {
        tvOutcome.text = msg
    }
}