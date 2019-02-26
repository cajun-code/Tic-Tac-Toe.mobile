package com.lasley.adam.codingtest.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.lasley.adam.codingtest.Objects.Board
import com.lasley.adam.codingtest.Objects.GamePrefs
import com.lasley.adam.codingtest.R
import com.lasley.adam.codingtest.algorithms.BasicWinner
import com.lasley.adam.codingtest.views.BoardView.Companion.GAME_Tie
import kotlinx.android.synthetic.main.fragment_gameplay.*


class Gameplay : Fragment() {

    var score_player = 0
    var score_ai = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gameplay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val board = Board(GamePrefs().boardSize + 3)

        boardView.setBoard(board)
        boardView.AIPlayer = BasicWinner(-1)
        setupControls()
        setupObservers()
        Score.text = getString(R.string.Game_ScoreChart, score_player, score_ai)

        val showTurn = (GamePrefs().PlayerCount > 1 || GamePrefs().AICount > 1)
        txt_turnText.visibility = if (showTurn) View.VISIBLE else View.INVISIBLE
        gTurn.visibility = txt_turnText.visibility
    }

    private fun setupControls() {
        btnMenu.setOnClickListener {
            if (score_player == 0 || score_ai == 0) {
                activity?.supportFragmentManager?.popBackStack()
                return@setOnClickListener
            }

            AlertDialog.Builder(this.requireContext()).apply {
                setTitle(getString(R.string.Game_exitgame))
                setMessage(getString(R.string.Game_ExitGame_msg))

                setPositiveButton(getString(R.string.Prompt_Yes)) { dialog, which ->
                    activity?.supportFragmentManager?.popBackStack()
                }

                setNegativeButton(getString(R.string.Prompt_No)) { dialog, which -> }
            }.show()
        }

        btn_Reply.setOnClickListener {
            txt_Winner.visibility = View.INVISIBLE
            btn_Reply.visibility = View.INVISIBLE
            boardView.ResetBoard()
        }
    }

    private fun setupObservers() {
        boardView.GameWinner.observe(this, Observer { winner ->
            run {
                if (winner == GAME_Tie) {
                    txt_Winner.visibility = View.VISIBLE
                    txt_Winner.text = getString(R.string.Game_End_Tie)
                    btn_Reply.visibility = View.VISIBLE

                    btn_Reply.visibility = if (winner != 0) View.VISIBLE else View.INVISIBLE
                } else {
                    if (winner > 0)
                        score_player++
                    else if (winner < 0)
                        score_ai++

                    if (winner != 0) {
                        txt_Winner.visibility = View.VISIBLE
                        txt_Winner.text = getString(
                            R.string.Game_End_Winner,
                            getString(if (winner > 0) R.string.Game_Active_You else R.string.Game_Active_AI)
                        )
                        btn_Reply.visibility = View.VISIBLE

                        btn_Reply.visibility = if (winner != 0) View.VISIBLE else View.INVISIBLE
                        Score.text = getString(R.string.Game_ScoreChart, score_player, score_ai)
                    }
                }

                Unit
            }
        })

        boardView.Turn.observe(this, Observer { turn ->
            run {
                if (turn > 0) {
                    if (GamePrefs().PlayerCount == 1)
                        gTurn.text = getString(R.string.Game_Active_You)
                    else
                        gTurn.text = getString(R.string.Game_Active_PlayerMany, turn)
                } else {
                    if (GamePrefs().AICount == 1)
                        gTurn.text = getString(R.string.Game_Active_AI)
                    else
                        gTurn.text = getString(R.string.Game_Active_AIMany, Math.abs(turn))
                }

                false
            }
        })
    }
}
