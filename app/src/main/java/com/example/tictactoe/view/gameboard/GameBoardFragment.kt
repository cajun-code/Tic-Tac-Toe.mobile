package com.example.tictactoe.view.gameboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.R
import com.example.tictactoe.model.Game
import com.example.tictactoe.utils.isBoardFilled
import com.example.tictactoe.utils.miniMax
import com.example.tictactoe.utils.winning
import kotlinx.android.synthetic.main.tic_tac_toe_board.*
import rx.Single
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class GameBoardFragment : Fragment(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var isNotPlayersTurn: Boolean = false
    private lateinit var listener: FragmentInteractionListener

    private lateinit var game: Game

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tic_tac_toe_board, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btn9.setOnClickListener(this)
    }

    fun setListener(listener: FragmentInteractionListener) {
        this.listener = listener
    }

    override fun onClick(view: View?) {
        if (isNotPlayersTurn || (view as AppCompatButton).text.isNotEmpty()) {
            return
        }

        when (view.id) {
            btn1.id -> setBoard(0, btn1)
            btn2.id -> setBoard(1, btn2)
            btn3.id -> setBoard(2, btn3)
            btn4.id -> setBoard(3, btn4)
            btn5.id -> setBoard(4, btn5)
            btn6.id -> setBoard(5, btn6)
            btn7.id -> setBoard(6, btn7)
            btn8.id -> setBoard(7, btn8)
            btn9.id -> setBoard(8, btn9)
        }
    }

    private fun setBoard(index: Int, btn: AppCompatButton) {
        listener.aisTurn()
        btn.text = game.player
        game.board[index] = game.player
        isNotPlayersTurn = true

        if (isGameOver()) {
            return
        }

        // finds best possible ai move on background thread
        Single.fromCallable { game.miniMax(game.ai) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                println("The move is ${it.index}: and score is ${it.score}")
                game.board[it.index] = game.ai
                setAiMove()
                if (isGameOver()) {
                    return@subscribe
                }
                listener.playersTurn()
                isNotPlayersTurn = false
            }
    }

    private fun isGameOver(): Boolean {
        when {
            game.winning(game.player) -> {
                listener.playAgain(10)
                return true
            }
            game.winning(game.ai) -> {
                listener.playAgain(-10)
                return true
            }
            game.isBoardFilled() -> {
                listener.playAgain(0)
                return true
            }
        }
        return false
    }

    private fun setAiMove() {
        when (game.aiMove.index) {
            0 -> btn1.text = game.ai
            1 -> btn2.text = game.ai
            2 -> btn3.text = game.ai
            3 -> btn4.text = game.ai
            4 -> btn5.text = game.ai
            5 -> btn6.text = game.ai
            6 -> btn7.text = game.ai
            7 -> btn8.text = game.ai
            8 -> btn9.text = game.ai
        }
    }

    fun setGame(game: Game) {
        this.game = game
    }

    fun clear() {
        btn1.text = ""
        btn2.text = ""
        btn3.text = ""
        btn4.text = ""
        btn5.text = ""
        btn6.text = ""
        btn7.text = ""
        btn8.text = ""
        btn9.text = ""
        isNotPlayersTurn = false
    }

    interface FragmentInteractionListener {
        fun playAgain(score: Int)
        fun playersTurn()
        fun aisTurn()
    }

}