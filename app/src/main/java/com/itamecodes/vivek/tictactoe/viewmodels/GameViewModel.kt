package com.itamecodes.vivek.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itamecodes.vivek.tictactoe.models.GameBoard
import com.itamecodes.vivek.tictactoe.models.GameWon
import com.itamecodes.vivek.tictactoe.models.Move
import com.itamecodes.vivek.tictactoe.models.Player

class GameViewModel : ViewModel() {

    val signChoices = arrayOf("X", "O")

    private lateinit var mHumanPlayer: Player
    private lateinit var mAIPlayer: Player
    private lateinit var mOriginalBoard: GameBoard
    private var mPlayerTurn: Boolean = true
    lateinit var nextAIMove: MutableLiveData<Move>
    lateinit var isGameWon: MutableLiveData<GameWon>
    lateinit var isGameATie: MutableLiveData<Boolean>



    companion object {
        const val PLAYER_HUMAN: Int = 1
        const val PLAYER_AI: Int = 2
        val WIN_COMBINATIONS = arrayOf(intArrayOf(0, 1, 2)
                , intArrayOf(3, 4, 5)
                , intArrayOf(6, 7, 8)
                , intArrayOf(0, 3, 6)
                , intArrayOf(1, 4, 7)
                , intArrayOf(2, 5, 8)
                , intArrayOf(0, 4, 8)
                , intArrayOf(6, 4, 2)
        )
    }


    fun instantiateViewModel(playerChoice: String, playerTurn: Boolean) {
        mHumanPlayer = Player(PLAYER_HUMAN, playerChoice)
        when (playerChoice) {
            signChoices[0] -> mAIPlayer = Player(PLAYER_AI, signChoices[1])
            signChoices[1] -> mAIPlayer = Player(PLAYER_AI, signChoices[0])
        }
        mOriginalBoard = GameBoard(mutableListOf<String>("0", "1", "2", "3", "4", "5", "6", "7", "8"))
        mPlayerTurn = playerTurn
        nextAIMove = MutableLiveData()
        isGameWon = MutableLiveData()
        isGameATie = MutableLiveData()
    }

    /**
     * The computation of the next AI move is done here. It takes in the board object which is an array of 9 slots on
     * the tic tac toe board. The AI simulates a set of all future moves and assigns a score to each of the moves and returns
     * the move with the maximum score
     */
    private fun minimax(board: GameBoard, player: Player): Move {
        var availSpots = emptySlots()
        var gameWonPlayer = checkForWinnerNew(board, mHumanPlayer)
        var gameWonComputer = checkForWinnerNew(board, mAIPlayer)
        gameWonPlayer?.let{
            return Move("-1", -10)
        }
        gameWonComputer?.let{
            return Move("-1", 10)
        }
        if(availSpots.isEmpty()){
            return Move("-1", 0)
        }

        var moves = mutableListOf<Move>()
        availSpots.forEachIndexed { index, element ->
            var move: Move
            var boardStatusArray = board.board
            var indexMove = boardStatusArray[availSpots[index].toInt()]
            boardStatusArray[availSpots[index].toInt()] = player.sign
            if (player.sign == mAIPlayer.sign) {
                var result = minimax(board, mHumanPlayer)
                move = Move(indexMove, result.score)
            } else {
                var result = minimax(board, mAIPlayer)
                move = Move(indexMove, result.score)
            }

            boardStatusArray[availSpots[index].toInt()] = move.index
            moves.add(move)
        }
        var bestMove = 0
        if (player.sign == mAIPlayer.sign) {
            var bestScore = -10000
            moves.forEachIndexed { index, _ ->
                if (moves[index].score > bestScore) {
                    bestScore = moves[index].score
                    bestMove = index
                }
            }
        } else {
            var bestScore = 10000
            moves.forEachIndexed { index, move ->
                if (moves[index].score < bestScore) {
                    bestScore = moves[index].score
                    bestMove = index
                }
            }
        }
        return moves[bestMove]
    }

    private fun checkForWinnerNew(originalBoard: GameBoard, player: Player): GameWon? {
        var plays = mutableListOf<Int>()
        var gameWon: GameWon? = null
        val boardStatusArray = originalBoard.board
        for (i in 0 until boardStatusArray.count()) {
            if (boardStatusArray.get(i) === player.sign) {
                plays.add(i)
            }
        }


        //check if plays has any of the win combos
        for ((index, winComb) in WIN_COMBINATIONS.withIndex()) {
            //see if the element from plays exists in the i
            var gameLost = false
            for (elem in winComb) {
                if (plays.indexOf(elem) == -1) {
                    //game lost
                    gameLost = true
                    break;
                }
            }
            if (!gameLost) {
                gameWon = GameWon(index, player)
            }
        }
        return gameWon
    }

    fun makeAIMove() {
        val move = bestSpot()
        nextAIMove.postValue(move)
        mOriginalBoard.board[move.index.toInt()] = mAIPlayer.sign
        checkForWinOrTie(mAIPlayer)
    }

    fun checkForHumanPlayerWinOrTie(indexOfMove: Int) {
        mOriginalBoard.board[indexOfMove] = mHumanPlayer.sign
        checkForWinOrTie(mHumanPlayer)
    }

    private fun checkForWinOrTie(player: Player) {
        var gameWon = checkForWinnerNew(mOriginalBoard, player)
        gameWon?.let {
            isGameWon.postValue(gameWon)
        } ?: run {
            var isGameTied = checkForTie()
            isGameATie.postValue(isGameTied)
        }
    }

    private fun bestSpot(): Move {
        return minimax(mOriginalBoard, mAIPlayer)
    }

    private fun emptySlots(): List<String> {
        return mOriginalBoard.board.filter { it != "X" && it != "O" }
    }

    private fun checkForTie(): Boolean {
        if (emptySlots().isEmpty()) {
            return true
        }
        return false
    }


}