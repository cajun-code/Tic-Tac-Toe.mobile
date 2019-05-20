package com.example.tictactoe.view.main

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.tictactoe.R
import com.example.tictactoe.base.BaseActivity
import com.example.tictactoe.model.Game
import com.example.tictactoe.utils.showGameOver
import com.example.tictactoe.utils.showStartGame
import com.example.tictactoe.view.gameboard.GameBoardFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), GameBoardFragment.FragmentInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var gameStartDialog: AlertDialog? = null
    private var gameOverDialog: AlertDialog? = null
    private var gameObject: Game? = null
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var gameBoard: GameBoardFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainActivityViewModel::class.java]
        gameBoard = supportFragmentManager.findFragmentById(R.id.frag_board) as GameBoardFragment
        gameStartScreen()

        viewModel.startGame.observe(this, Observer {
            gameOverDialog = null
            if (it == "X")
                gameObject = Game("X", "O", Game.Move())
            else if (it == "O")
                gameObject = Game("O", "X", Game.Move())
            startGame()
        })


    }

    private fun startGame() {
        frame.text = "Your Turn"
        gameObject?.let {
            gameBoard.setGame(it)
        }
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        (fragment as GameBoardFragment).setListener(this)
    }

    private fun gameStartScreen() {
        if (gameStartDialog == null) {
            gameStartDialog = showStartGame {
                cancelable = false
                startGameClickListener {
                    if (gamePieceSelected().isNotEmpty()) {
                        viewModel.startGame(gamePieceSelected())
                        close()
                    } else
                        toast("Select X or O to start")

                }
                oClickListener {
                    changeBackground()
                }
                xClickListener {
                    changeBackground()
                }
            }
        }
        gameStartDialog?.show()
    }

    private fun gameOverScreen(result: String) {
        if (gameOverDialog == null) {
            gameOverDialog = showGameOver {
                cancelable = false
                setHeaderText(result)
                quitGameClickListener {
                    toast("Quit")
                    finish()
                }
                playAgainClickListener {
                    toast("Play Again")
                    gameBoard.clear()
                    gameStartScreen()
                }
            }
        }
        gameOverDialog?.show()
    }

    override fun playAgain(score: Int) {
        when (score) {
            -10 -> {
                frame.text = "You Lose"
                gameOverScreen("You Lose")
            }
            0 -> {
                frame.text = "It's a Tie"
                gameOverScreen("It's a Tie")

            }
            10 -> {
                frame.text = "You Win"
                gameOverScreen("You Win")

            }
        }
    }

    override fun playersTurn() {
        frame.text = "Your Turn"
    }

    override fun aisTurn() {
        frame.text = "Computer's Turn"
    }

}
