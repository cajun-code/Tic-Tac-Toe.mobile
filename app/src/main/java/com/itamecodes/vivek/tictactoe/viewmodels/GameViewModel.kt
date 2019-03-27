package com.itamecodes.vivek.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itamecodes.vivek.tictactoe.models.GameBoard
import com.itamecodes.vivek.tictactoe.models.GameWon
import com.itamecodes.vivek.tictactoe.models.Move
import com.itamecodes.vivek.tictactoe.models.Player

class GameViewModel : ViewModel() {

    val signChoices = arrayOf("X", "O")

    lateinit var humanPlayer: Player
    lateinit var aiPlayer: Player
    lateinit var originalBoard: GameBoard
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
        humanPlayer = Player(PLAYER_HUMAN, playerChoice)
        when (playerChoice) {
            signChoices[0] -> aiPlayer = Player(PLAYER_AI, signChoices[1])
            signChoices[1] -> aiPlayer = Player(PLAYER_AI, signChoices[0])
        }
        originalBoard = GameBoard(mutableListOf<String>("0", "1", "2", "3", "4", "5", "6", "7", "8"))
        mPlayerTurn = playerTurn
        nextAIMove = MutableLiveData()
        isGameWon = MutableLiveData()
        isGameATie = MutableLiveData()
    }

    fun minimax(board: GameBoard, player: Player): Move {
        var availSpots = emptySlots()
        var gameWonPlayer = checkForWinnerNew(board, humanPlayer)
        var gameWonComputer = checkForWinnerNew(board, aiPlayer)
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
            if (player.sign == aiPlayer.sign) {
                var result = minimax(board, humanPlayer)
                move = Move(indexMove, result.score)
            } else {
                var result = minimax(board, aiPlayer)
                move = Move(indexMove, result.score)
            }

            boardStatusArray[availSpots[index].toInt()] = move.index
            moves.add(move)
        }
        var bestMove: Int = 0
        if (player.sign == aiPlayer.sign) {
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
        var index = 0

        //check if plays has any of the win combos
        for (i in WIN_COMBINATIONS) {
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

    fun makeAIMove() {
        val move = bestSpot()
        nextAIMove.postValue(move)
        originalBoard.board[move.index.toInt()] = aiPlayer.sign
        checkForWinOrTie(aiPlayer)
    }

    fun checkForHumanPlayerWinOrTie(indexOfMove: Int) {
        originalBoard.board[indexOfMove] = humanPlayer.sign
        checkForWinOrTie(humanPlayer)
    }

    fun checkForWinOrTie(player: Player) {
        var gameWon = checkForWinnerNew(originalBoard, player)
        gameWon?.let {
            isGameWon.postValue(gameWon)
        } ?: run {
            var isGameTied = checkForTie()
            isGameATie.postValue(isGameTied)
        }
    }

    fun bestSpot(): Move {
        return minimax(originalBoard, aiPlayer)
    }

    fun emptySlots(): List<String> {
        return originalBoard.board.filter { it != "X" && it != "O" }
    }

    fun checkForTie(): Boolean {
        if (emptySlots().isEmpty()) {
            return true
        }
        return false
    }


}