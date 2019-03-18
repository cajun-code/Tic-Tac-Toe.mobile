package com.domschumerth.tictactoe.calculations

import com.domschumerth.tictactoe.objects.Board
import com.domschumerth.tictactoe.states.CellState

class Evaluate (board: Board, state1: CellState, state2: CellState){

    private val board: Board
    private val state1: CellState
    private val state2: CellState

    init {
        this.board = board
        this.state1 = state1
        this.state2 = state2
    }

    fun evaluate(): Int {
        var score = 0
        score += evaluateLine(0, 0, 0, 1, 0, 2)  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2)  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2)  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0)  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1)  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2)  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2)  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0)  // alternate diagonal
        return score
    }

    private fun evaluateLine(row1: Int, col1: Int, row2: Int, col2: Int, row3: Int, col3: Int) : Int {
        var score = 0

        // First cell
        if(board.cells[row1][col1]?.content == state1) {
            score = 1
        } else if (board.cells[row1][col1]?.content == state2) {
            score = -1
        }

        // Second cell
        if (board.cells[row2][col2]?.content == state1) {
            if (score == 1) {   // cell1 is mySeed
                score = 10
            } else if (score == -1) {  // cell1 is oppSeed
                return 0
            } else {  // cell1 is empty
                score = 1
            }
        } else if (board.cells[row2][col2]?.content == state2) {
            if (score == -1) { // cell1 is oppSeed
                score = -10
            } else if (score == 1) { // cell1 is mySeed
                return 0
            } else {  // cell1 is empty
                score = -1
            }
        }

        // Third cell
        if (board.cells[row3][col3]?.content == state1) {
            if (score > 0) {  // cell1 and/or cell2 is mySeed
                score *= 10
            } else if (score < 0) {  // cell1 and/or cell2 is oppSeed
                return 0
            } else {  // cell1 and cell2 are empty
                score = 1
            }
        } else if (board.cells[row3][col3]?.content == state2) {
            if (score < 0) {  // cell1 and/or cell2 is oppSeed
                score *= 10
            } else if (score > 1) {  // cell1 and/or cell2 is mySeed
                return 0
            } else {  // cell1 and cell2 are empty
                score = -1
            }
        }
        return score
    }
}
