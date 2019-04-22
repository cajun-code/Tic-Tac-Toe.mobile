package com.kanathala.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_player_selection.*
import java.util.*

class GameActivity : AppCompatActivity() {

    var aiPiece = "X"
    var playerPiece = "O"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var gameBoardArray = arrayListOf<String>("", "", "", "", "", "", "", "", "")

        //getting the data from intent
        var intent : Intent = getIntent()
        aiPiece = intent.getStringExtra("ai")
        playerPiece = intent.getStringExtra("player")


        // click listeners for board
        button0.setOnClickListener {
            if (gameBoardArray[0] == "") {
                button0.text = playerPiece
                gameBoardArray[0] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button1.setOnClickListener {
            if (gameBoardArray[1] == "") {
                button1.text = playerPiece
                gameBoardArray[1] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button2.setOnClickListener {
            if (gameBoardArray[2] == "") {
                button2.text = playerPiece
                gameBoardArray[2] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button3.setOnClickListener {
            if (gameBoardArray[3] == "") {
                button3.text = playerPiece
                gameBoardArray[3] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button4.setOnClickListener {
            if (gameBoardArray[4] == "") {
                button4.text = playerPiece
                gameBoardArray[4] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button5.setOnClickListener {
            if (gameBoardArray[5] == "") {
                button5.text = playerPiece
                gameBoardArray[5] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button6.setOnClickListener {
            if (gameBoardArray[6] == "") {
                button6.text = playerPiece
                gameBoardArray[6] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button7.setOnClickListener {
            if (gameBoardArray[7] == "") {
                button7.text = playerPiece
                gameBoardArray[7] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        button8.setOnClickListener {
            if (gameBoardArray[8] == "") {
                button8.text = playerPiece
                gameBoardArray[8] = playerPiece
                if(!isBoardFull(gameBoardArray) && !result(gameBoardArray, playerPiece)) {
                    val position = getAIMove(gameBoardArray)
                    gameBoardArray[position] = aiPiece
                    displayComputerButton(position)
                }
            }
            checkResult(gameBoardArray)
        }

        buttonReset.setOnClickListener{
            val intent = Intent(this@GameActivity,GameActivity::class.java)
            intent.putExtra("player",playerPiece)
            intent.putExtra("ai",aiPiece)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent)
        }

        // back ImageView
        imageViewBack.setOnClickListener{
            finish()
        }

    }

    private fun checkResult(board: ArrayList<String>){
        if(result(board, playerPiece)){
            Toast.makeText(this, "Player wins.Reset the game to start new game.", Toast.LENGTH_LONG).show()
            disableButtons()
        }else if(result(board, aiPiece)){
            Toast.makeText(this, "AI wins.Reset the game to start new game.", Toast.LENGTH_LONG).show()
            disableButtons()
        }
        if(isBoardFull(board)){
            Toast.makeText(this, "Game is Tie.Reset the game to start new game.", Toast.LENGTH_LONG).show()
            disableButtons()
        }

    }

    // disabling all the buttons in board
    private fun disableButtons() {
        button0.isClickable = false
        button1.isClickable = false
        button2.isClickable = false
        button3.isClickable = false
        button4.isClickable = false
        button5.isClickable = false
        button6.isClickable = false
        button7.isClickable = false
        button8.isClickable = false

    }

    // checking the board filled status
    private fun isBoardFull(board: ArrayList<String>): Boolean {
        for (i in board)
            if(i != playerPiece && i != aiPiece) return false
        return true
    }

    // validating the result
    private fun result(bd: ArrayList<String>, s: String): Boolean =
        if(bd[0] == s && bd[1] == s && bd[2] == s) true
        else if(bd[3] == s && bd[4] == s && bd[5] == s) true
        else if(bd[6] == s && bd[7] == s && bd[8] == s) true
        else if(bd[0] == s && bd[3] == s && bd[6] == s) true
        else if(bd[1] == s && bd[4] == s && bd[7] == s) true
        else if(bd[2] == s && bd[5] == s && bd[8] == s) true
        else if(bd[0] == s && bd[4] == s && bd[8] == s) true
        else if(bd[2] == s && bd[4] == s && bd[6] == s) true
        else false

    // for handling back buttton of the Android Device
    override fun onBackPressed() {
        super.onBackPressed()
    }

    // getting the ai moves
    private fun getAIMove(board: ArrayList<String>): Int {

        //check if computer can win in this move
        for (i in 0..board.count()-1){
            var copy: ArrayList<String> = getBoardCopy(board)
            if(copy[i] == "") copy[i] = aiPiece
            if(result(copy, aiPiece)) return i
        }

        //check if player could win in the next move
        for (i in 0..board.count()-1){
            var copy2 = getBoardCopy(board)
            if(copy2[i] == "") copy2[i] = playerPiece
            if(result(copy2, playerPiece)) return i
        }

        //try to take corners if its free
        var move = choseRandomMove(board, arrayListOf<Int>(0, 2, 6, 8))
        if(move != -1)
            return move

        //try to take center if its free
        if(board[4] == "") return 4

        //move on one of the sides
        return choseRandomMove(board, arrayListOf<Int>(1, 3, 5, 7))
    }

    private fun choseRandomMove(board: ArrayList<String>, list: ArrayList<Int>): Int {
        var possibleMoves = arrayListOf<Int>()
        for (i in list){
            if(board[i] == "") possibleMoves.add(i)
        }
        if(possibleMoves.isEmpty()) return -1
        else {
            var index = Random().nextInt(possibleMoves.count())
            return possibleMoves[index]
        }
    }

    private fun getBoardCopy(board: ArrayList<String>): ArrayList<String> {
        var bd = arrayListOf<String>("", "", "", "", "", "", "", "", "")
        for (i in 0..board.count()-1) {
            bd[i] = board[i]
        }
        return bd
    }

    private fun displayComputerButton(position: Int){
        if(position == 0) button0.text = aiPiece
        else if(position == 1) button1.text = aiPiece
        else if(position == 2) button2.text = aiPiece
        else if(position == 3) button3.text = aiPiece
        else if(position == 4) button4.text = aiPiece
        else if(position == 5) button5.text = aiPiece
        else if(position == 6) button6.text = aiPiece
        else if(position == 7) button7.text = aiPiece
        else if(position == 8) button8.text = aiPiece
    }

}

