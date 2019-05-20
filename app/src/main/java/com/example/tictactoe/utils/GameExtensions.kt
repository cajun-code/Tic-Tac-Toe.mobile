package com.example.tictactoe.utils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.model.Game

/*
 * Game Start Dialog
 */

inline fun Activity.showStartGame(func: GameStartDialogHelper.() -> Unit): AlertDialog =
    GameStartDialogHelper(this).apply {
        func()
    }.create()

inline fun Activity.showGameOver(func: GameOverDialogHelper.() -> Unit): AlertDialog =
    GameOverDialogHelper(this).apply {
        func()
    }.create()

fun Game.isBoardFilled(): Boolean {
    return board.filter { s ->
        s != "O" && s != "X"
    }.isEmpty()
}

fun Game.miniMax(player: String): Game.Move {

    // available spots
    val availSpots = board.filter { s ->
        s != "O" && s != "X"
    }

    // checks for the terminal states such as win, lost and tie and returning a value accordingly
    if (winning(player)) {
        return Game.Move(score = -10)
    } else if (winning(ai)) {
        return Game.Move(score = 10)
    } else if (availSpots.isEmpty()) {
        return Game.Move(score = 0)
    }

    val moves = mutableListOf<Game.Move>()

    // loop through available spots
    if (availSpots.isNotEmpty()) {
        for (i in 0 until availSpots.size) {
            // create an object for each and store the index of that spot that was stored as a number in the object's index key
            val move = Game.Move()
            move.index = board[availSpots[i] as Int] as Int

            // set the empty spot to the current player
            board[availSpots[i] as Int] = player

            // if collect the score resulted from calling minimax on the opponent of the current player
            if (player == ai) {
                val result = miniMax(this.player)
                move.score = result.score
            } else {
                val result = miniMax(this.ai)
                move.score = result.score
            }

            // reset the spot to empty
            board[availSpots[i] as Int] = move.index

            // add the object to the array
            moves.add(move)
        }
    }

    // if it is the computer's turn loop over the moves and choose the move with the highest score
    var bestMove: Int = Int.MIN_VALUE
    if (player == ai) {
        var bestScore = -10000
        for (i in 0 until moves.size) {
            if (moves[i].score > bestScore) {
                bestScore = moves[i].score
                bestMove = i
            }
        }
    } else {
        // else loop over the moves and choose the move with the lowest score
        var bestScore = 10000
        for (i in 0 until moves.size) {
            if (moves[i].score < bestScore) {
                bestScore = moves[i].score
                bestMove = i
            }
        }
    }
    aiMove = moves[bestMove]
    // return the chosen move (object) from the array to the higher depth
    return moves[bestMove]
}

fun Game.winning(player: String): Boolean = (board[0] == player && board[1] == player && board[2] == player) ||
        (board[3] == player && board[4] == player && board[5] == player) ||
        (board[6] == player && board[7] == player && board[8] == player) ||
        (board[0] == player && board[3] == player && board[6] == player) ||
        (board[1] == player && board[4] == player && board[7] == player) ||
        (board[2] == player && board[5] == player && board[8] == player) ||
        (board[0] == player && board[4] == player && board[8] == player) ||
        (board[2] == player && board[4] == player && board[6] == player)