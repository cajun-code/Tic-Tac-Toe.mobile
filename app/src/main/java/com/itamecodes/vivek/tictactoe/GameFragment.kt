package com.itamecodes.vivek.tictactoe

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.itamecodes.vivek.tictactoe.extensions.getButtonDynamically
import com.itamecodes.vivek.tictactoe.models.GameWon
import com.itamecodes.vivek.tictactoe.models.Move
import com.itamecodes.vivek.tictactoe.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.game_fragment.*

class GameFragment : Fragment(), View.OnClickListener {


    private lateinit var model: GameViewModel
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

    private var origBoard = mutableListOf<String>("0", "1", "2", "3", "4", "5", "6", "7", "8")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    private fun startGame() {
        if (playerTurn) {
            Toast.makeText(activity, "Your Turn", Toast.LENGTH_LONG).show()
        } else {
            makeAIPlay()
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
                        startGame()
                    }.setNegativeButton("No") { _, _ ->
                        playerChoice = "O"
                        computerChoice = "X"
                        startGame()
                    }.create().show()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            model = ViewModelProviders.of(it).get(GameViewModel::class.java)
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
            playerTurn = false
            //TODO just use a tag
            val idString = resources.getResourceEntryName(button.id)
            val index = idString.split("_")[1].toInt()
            origBoard[index] = playerChoice

            var isGameWon: GameWon? = checkForWinnerNew(origBoard, playerChoice)

            isGameWon?.let {
                declareWinner("You Win")
            } ?: run {
                if (!checkForTie()) {
                    //make player play
                    makeAIPlay()
                }

            }
        }
    }

    fun makeAIPlay() {
        //loop through array and find empty box
        val nextMove = bestSpot().toInt()
        getButtonDynamically("box_$nextMove").text = computerChoice
        getButtonDynamically("box_$nextMove").isEnabled = false
        origBoard[nextMove] = computerChoice

        var isGameWon: GameWon? = checkForWinnerNew(origBoard, computerChoice)

        isGameWon?.let {
            showWinningBoxes(isGameWon)
            declareWinner("I Win")
        } ?: run {
            if (!checkForTie()) {
                //make player play
                playerTurn = true
            }
        }

    }

    private fun showWinningBoxes(gameWon: GameWon) {
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
                        showDialogForTurnChosing()
                        dialog.dismiss()
                    }.setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                        findNavController(box_0).navigate(R.id.action_gameFragment_to_splashFragment, null)
                    }.create().show()
        }
    }

    fun resetScreen() {
        origBoard = mutableListOf<String>("0", "1", "2", "3", "4", "5", "6", "7", "8")
        setBoxes.forEachIndexed { index,i ->
            getButtonDynamically("box_$i").text = ""
            getButtonDynamically("box_$i").isEnabled = true
            getButtonDynamically("box_$i").setBackgroundColor(Color.WHITE)
        }
    }


    private fun checkForWinnerNew(originalBoard: MutableList<String>, player: String): GameWon? {
        var plays = mutableListOf<Int>()
        var gameWon: GameWon? = null
        for (i in 0 until originalBoard.count()) {
            if (originalBoard.get(i) === player) {
                plays.add(i)
            }
        }
        var index = 0

        //check if plays has any of the win combos
        for (i in winCombinations) {
            //see if the element from plays exists in the i
            var gameLost = false
            for (elem in i) {
                if (plays.indexOf(elem) == -1) {
                    //game lost
                    gameLost = true
                    break;
                }
            }
            if (!gameLost) {
                gameWon = GameWon(index, player)
            }
            index++;
        }
        return gameWon
    }


    fun bestSpot(): String {
        var index:String =minimax(origBoard,computerChoice).index
        return index
    }

    fun emptySlots(): List<String> {
        return origBoard.filter { it != "X" && it != "O" }
    }

    fun checkForTie(): Boolean {
        if (emptySlots().isEmpty()) {
            declareWinner("Game Tied")
            return true
        }
        return false
    }

    fun minimax(board:MutableList<String>,player:String): Move {
        var availSpots = emptySlots()
        var gameWon = checkForWinnerNew(board,player)
        gameWon?.let{
            when(gameWon.player){
                playerChoice-> return Move("-1",-10)
                else -> return Move("-1",20)
            }
        }?:run{
            if(availSpots.isEmpty()) return Move("-1",0)
        }
        var moves = mutableListOf<Move>()
        availSpots.forEachIndexed { index, element ->
            var move:Move
            var indexMove = board[availSpots[index].toInt()]
            board[availSpots[index].toInt()] = player
            if(player == computerChoice){
                var result = minimax(board,playerChoice)
                move= Move(indexMove,result.score)
            }else{
                var result = minimax(board,computerChoice)
                move= Move(indexMove,result.score)
            }

            board[availSpots[index].toInt()] = move.index
            moves.add(move)
        }
        var bestMove:Int =0
        if(player== computerChoice){
            var bestScore = -10000
            moves.forEachIndexed { index, move ->
                if(moves[index].score>bestScore){
                    bestScore= moves[index].score
                    bestMove = index
                }
            }
        }else{
            var bestScore = 10000
            moves.forEachIndexed { index, move ->
                if(moves[index].score<bestScore){
                    bestScore= moves[index].score
                    bestMove = index
                }
            }
        }
        return moves[bestMove]
    }

}