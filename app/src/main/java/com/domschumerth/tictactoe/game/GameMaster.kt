package com.domschumerth.tictactoe.game

import com.domschumerth.tictactoe.calculations.WinCheck
import com.domschumerth.tictactoe.objects.Board
import com.domschumerth.tictactoe.objects.Cell
import com.domschumerth.tictactoe.states.CellState
import com.domschumerth.tictactoe.states.GameState

class GameMaster {

    private var currentState = GameState.PLAYING
    private var currentPlayer = CellState.X

    val board = Board()

    private var gameStatusListener: OnGameStatusChange? = null

    fun initGame(side: CellState) {
        board.init()
        currentState = GameState.PLAYING
        currentPlayer = side
    }

    private fun updateGame(player: CellState) {
        if (WinCheck().won(player, board)) {
            currentState = when (player) {
                CellState.X -> GameState.X_WON
                else -> GameState.O_WON
            }
        } else if (WinCheck().tie(board)) {
            currentState = GameState.TIE
        }

        gameStatusListener?.onStatusChange(currentState)
    }

    fun playerMove(player: CellState, row: Int, col: Int) : Cell? {
        currentPlayer = player
        if (row >= 0 && row < board.ROW && col >= 0 && col < board.COL
            && board.cells[row][col]?.content == CellState.NULL) {
            board.cells[row][col]?.content = player
            board.currentRow = row
            board.currentCol = col

            val cell = Cell(row, col)
            cell.content = player

            updateGame(player)
            return cell
        }
        return null
    }

    fun setOnGameStatusChangeListener(listener: OnGameStatusChange?){
        this.gameStatusListener = listener
    }

    interface OnGameStatusChange {
        fun onStatusChange(gameState: GameState)
    }
}