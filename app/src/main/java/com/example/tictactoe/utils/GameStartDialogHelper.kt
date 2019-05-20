package com.example.tictactoe.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.tictactoe.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class GameStartDialogHelper(private val context: Context) : BaseDialogHelper() {

    companion object {
        const val X = "X"
        const val O = "O"
    }

    private var selectedCard: MaterialCardView? = null
    private var selectionPiece: String = ""

    //  dialog view
    override val dialogView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.start_game_dialog, null)
    }

    override val builder: AlertDialog.Builder = AlertDialog.Builder(context).setView(dialogView)


    //  start game button
    private val startGame: MaterialButton by lazy {
        dialogView.findViewById<MaterialButton>(R.id.btn_start_game)
    }

    //  x button
    private val xOption: MaterialCardView by lazy {
        dialogView.findViewById<MaterialCardView>(R.id.cv_x)
    }

    //  o button
    private val oOption: MaterialCardView by lazy {
        dialogView.findViewById<MaterialCardView>(R.id.cv_o)
    }


    //  startGameClickListener with listener
    fun startGameClickListener(func: (() -> Unit)? = null) =
        with(startGame) {
            setClickListenerToDialogIcon(func, null)
        }

    //  selectXClickListener with listener
    fun xClickListener(func: (() -> Unit)? = null) =
        with(xOption) {
            setClickListenerToDialogIcon(func, this)
        }

    //  selectOGameClickListener with listener
    fun oClickListener(func: (() -> Unit)? = null) =
        with(oOption) {
            setClickListenerToDialogIcon(func, this)
        }

    fun gamePieceSelected(): String = selectionPiece

    fun close() {
        dialog?.dismiss()
    }

    fun changeBackground() {
        selectedCard?.let { card ->
            when (card) {
                xOption -> {
                    selectionPiece = X
                    xOption.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorAccent
                        )
                    )
                    oOption.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
                }
                oOption -> {
                    selectionPiece = O
                    oOption.setCardBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorAccent
                        )
                    )
                    xOption.setCardBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
                }
            }
        }
    }

    //  view click listener as extension function
    private fun View.setClickListenerToDialogIcon(func: (() -> Unit)?, card: MaterialCardView?) =
        setOnClickListener {
            card?.let {
                when (it) {
                    xOption -> selectedCard = xOption
                    oOption -> selectedCard = oOption
                }
            }
            func?.invoke()
        }
}