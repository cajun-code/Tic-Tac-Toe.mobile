package com.itamecodes.vivek.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
    private var playerChoice: String = "X"
    private var computerChoice: String = "O"
    private var playerTurn: Boolean = true
    private var setBoxes = mutableSetOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

    val winCombinations = arrayOf(intArrayOf(0, 1, 2)
            , intArrayOf(3, 4, 5)
            , intArrayOf(6, 7, 8)
            , intArrayOf(0, 3, 6)
            , intArrayOf(1, 4, 7)
            , intArrayOf(2, 5, 8)
            , intArrayOf(0, 4, 8)
            , intArrayOf(6, 4, 2)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        showDialogForTurnChosing()
    }

    fun showDialogForTurnChosing() {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            return@let dialogBuilder.setMessage("Do you want to go first")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        playerTurn = true
                        showDialogForSignChosing()
                    }.setNegativeButton("No") { _, _ ->
                        playerTurn = false
                        showDialogForSignChosing()
                    }.create().show()
        }

    }

    private fun showDialogForSignChosing() {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            return@let dialogBuilder.setMessage("I guess You want to chose the sign 'X' ")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        playerChoice = "X"
                        computerChoice = "O"
                        initListenersAndStartGame()
                    }.setNegativeButton("No") { _, _ ->
                        playerChoice = "O"
                        computerChoice = "X"
                        initListenersAndStartGame()
                    }.create().show()
        }

    }

    fun setNextMoveListener(){
        mGameViewModel.nextAIMove.observe(this, Observer {
            val nextMove = it.index.toInt()
            getButtonDynamically("box_$nextMove").text = computerChoice
            getButtonDynamically("box_$nextMove").isEnabled = false
        })
    }

    fun isGameWonListener(){
        mGameViewModel.isGameWon.observe(this, Observer {
          it?.let{
              //declare winner
              highlightWinningBoxes(it)
              if(it.player.type == GameViewModel.PLAYER_AI) declareWinner("I win") else declareWinner("You win")
          }
        })
    }

    fun isGameATieListener(){
        mGameViewModel.isGameATie.observe(this, Observer {isTied->
            if(isTied){
                declareWinner("Game Tied")
            }else{
                playerTurn = !playerTurn
                if(playerTurn){
                    Toast.makeText(activity, "Your Turn", Toast.LENGTH_LONG).show()
                }else{
                    mGameViewModel.makeAIMove()
                }
            }
        })
    }

    private fun initListenersAndStartGame() {
        mGameViewModel.instantiateViewModel(playerChoice,playerTurn)
        setNextMoveListener()
        isGameATieListener()
        isGameWonListener()
        if (playerTurn) {
            Toast.makeText(activity, "Your Turn", Toast.LENGTH_LONG).show()
        } else {
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

    fun setClickListeners() {
        setBoxes.forEach {
            getButtonDynamically("box_$it").setOnClickListener(this)
        }
    }


    override fun onClick(button: View?) {
        if (playerTurn) {
            (button as Button).text = playerChoice
            button.isEnabled = false
            //TODO just use a tag
            val idString = resources.getResourceEntryName(button.id)
            val index = idString.split("_")[1].toInt()
            mGameViewModel.checkForHumanPlayerWinOrTie(index)

        }
    }

    private fun highlightWinningBoxes(gameWon: GameWon) {
        val winningCombination = winCombinations[gameWon.index]
        winningCombination.forEachIndexed { index, i ->
            getButtonDynamically("box_$i").setBackgroundColor(Color.RED)
        }
    }

    fun declareWinner(message: String) {
        context?.let {
            val dialogBuilder = AlertDialog.Builder(it)
            return@let dialogBuilder.setMessage("$message. Do you want to restart")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, _ ->
                        resetScreen()
                        dialog.dismiss()
                        showDialogForTurnChosing()
                    }.setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                        findNavController(box_0).navigate(R.id.action_gameFragment_to_splashFragment, null)
                    }.create().show()
        }
    }

    fun resetScreen() {
        setBoxes.forEachIndexed { index,i ->
            getButtonDynamically("box_$i").text = ""
            getButtonDynamically("box_$i").isEnabled = true
            getButtonDynamically("box_$i").setBackgroundColor(Color.WHITE)
        }
        mGameViewModel.nextAIMove.removeObservers(this)
        mGameViewModel.isGameATie.removeObservers(this)
        mGameViewModel.isGameWon.removeObservers(this)
    }







}