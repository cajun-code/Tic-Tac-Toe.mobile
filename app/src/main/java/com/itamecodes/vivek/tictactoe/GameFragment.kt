package com.itamecodes.vivek.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.itamecodes.vivek.tictactoe.extensions.getButtonDynamically
import com.itamecodes.vivek.tictactoe.models.GameWon
import com.itamecodes.vivek.tictactoe.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.game_fragment.*

class GameFragment : Fragment(), View.OnClickListener {

    private lateinit var mGameViewModel: GameViewModel
    private var mPlayerChoice: String = "X" //TODO try to use this from the viewmodel
    private var mComputerChoice: String = "O" //TODO try to use this from the viewmodel
    private var mPlayerTurn: Boolean = true //TODO try to use this from the viewmodel
    //just a helper to loop through the buttons
    private var mSetBoxes = mutableSetOf(0, 1, 2, 3, 4, 5, 6, 7, 8)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        showDialogForTurnChosing()
    }

    //TODO  these dialogs could be better than alert dialogs and should be controlled by the viewmodel
    private fun showDialogForTurnChosing() {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            return@let dialogBuilder.setMessage(getString(R.string.go_first))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        mPlayerTurn = true
                        showDialogForSignChosing()
                    }.setNegativeButton(getString(R.string.no)) { _, _ ->
                        mPlayerTurn = false
                        showDialogForSignChosing()
                    }.create().show()
        }

    }

    //TODO  these dialogs could be better than alert dialogs and should be controlled by the viewmodel
    private fun showDialogForSignChosing() {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            return@let dialogBuilder.setMessage(getString(R.string.choseX))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        mPlayerChoice = "X"
                        mComputerChoice = "O"
                        initListenersAndStartGame()
                    }.setNegativeButton(getString(R.string.no)) { _, _ ->
                        mPlayerChoice = "O"
                        mComputerChoice = "X"
                        initListenersAndStartGame()
                    }.create().show()
        }

    }

    private fun setNextMoveListener() {
        mGameViewModel.nextAIMove.observe(this, Observer {
            val nextMove = it.index.toInt()
            getButtonDynamically("box_$nextMove").text = mComputerChoice
            getButtonDynamically("box_$nextMove").isEnabled = false
        })
    }

    private fun isGameWonListener() {
        mGameViewModel.isGameWon.observe(this, Observer {
            it?.let {
                //declare winner
                highlightWinningBoxes(it)
                if (it.player.type == GameViewModel.PLAYER_AI) declareWinner(getString(R.string.ai_win_message)) else declareWinner(getString(R.string.human_win_message))
            }
        })
    }

    private fun isGameATieListener() {
        mGameViewModel.isGameATie.observe(this, Observer { isTied ->
            if (isTied) {
                declareWinner(getString(R.string.game_tied))
            } else {
                mPlayerTurn = !mPlayerTurn
                if (!mPlayerTurn) {
                    mGameViewModel.makeAIMove()
                }
            }
        })
    }

    private fun initListenersAndStartGame() {
        mGameViewModel.instantiateViewModel(mPlayerChoice, mPlayerTurn)
        setNextMoveListener()
        isGameATieListener()
        isGameWonListener()
        if (!mPlayerTurn) {
            mGameViewModel.makeAIMove()
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            mGameViewModel = ViewModelProviders.of(it).get(GameViewModel::class.java)
        }
        setClickListeners()
    }

    private fun setClickListeners() {
        mSetBoxes.forEach {
            getButtonDynamically("box_$it").setOnClickListener(this)
        }
    }


    override fun onClick(button: View?) {
        if (mPlayerTurn) {
            (button as Button).text = mPlayerChoice
            button.isEnabled = false
            //TODO just use a tag
            val idString = resources.getResourceEntryName(button.id)
            val index = idString.split("_")[1].toInt()
            mGameViewModel.checkForHumanPlayerWinOrTie(index)
        }
    }

    private fun highlightWinningBoxes(gameWon: GameWon) {
        val winningCombination = GameViewModel.WIN_COMBINATIONS[gameWon.index]
        winningCombination.forEachIndexed { _, i ->
            getButtonDynamically("box_$i").setBackgroundColor(Color.RED)
        }
    }

    private fun declareWinner(message: String) {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            return@let dialogBuilder.setMessage(getString(R.string.game_restart).format(message))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        resetScreen()
                        dialog.dismiss()
                        showDialogForTurnChosing()
                    }.setNegativeButton(getString(R.string.no)) { dialog, which ->
                        dialog.dismiss()
                        findNavController(box_0).navigate(R.id.action_gameFragment_to_splashFragment, null)
                    }.create().show()
        }
    }

    private fun resetScreen() {
        mSetBoxes.forEachIndexed { _, i ->
            getButtonDynamically("box_$i").text = ""
            getButtonDynamically("box_$i").isEnabled = true
            getButtonDynamically("box_$i").setBackgroundColor(Color.WHITE)
        }
        mGameViewModel.nextAIMove.removeObservers(this)
        mGameViewModel.isGameATie.removeObservers(this)
        mGameViewModel.isGameWon.removeObservers(this)
    }


}