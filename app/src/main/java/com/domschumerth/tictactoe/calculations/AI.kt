package com.domschumerth.tictactoe.calculations

import com.domschumerth.tictactoe.objects.Board
import com.domschumerth.tictactoe.states.CellState

class AI(board: Board) {

    private val board: Board
    private var cells = board.cells

    var state1: CellState
    var state2: CellState

    init {
        state1 = CellState.NULL
        state2 = CellState.NULL
        this.board = board
    }

    fun setState(cellState: CellState) {
        state1 = cellState
        state2 = when (state1) {
            CellState.X -> CellState.O
            else -> CellState.X
        }
    }

    fun move(): Array<Int>? {
        val result = readBoard(7, state1)
        return arrayOf(result[1], result[2])
    }

    private fun readBoard(depth: Int, player: CellState): Array<Int> {
        val nextMoves: MutableList<Array<Int>> = generateMoves()

        var bestScore = when (state1) {
            player -> Int.MIN_VALUE
            else -> Int.MAX_VALUE
        }
        var currentScore: Int
        var bestRow = -1
        var bestCol = -1

        if(nextMoves.isEmpty() || depth == 0){
            bestScore = Evaluate(board, state1, state2).evaluate()
        } else {
            nextMoves.forEach {
                cells[it[0]][it[1]]?.content = player

                if (player == state1){
                    currentScore = readBoard(depth - 1, state2)[0]
                    if(currentScore > bestScore) {
                        bestScore = currentScore
                        bestRow = it[0]
                        bestCol = it[1]
                    }
                } else {
                    currentScore = readBoard(depth - 1, state1)[0]
                    if(currentScore < bestScore) {
                        bestScore = currentScore
                        bestRow = it[0]
                        bestCol = it[1]
                    }
                }
                cells[it[0]][it[1]]?.content = CellState.NULL
            }
        }
        return arrayOf(bestScore, bestRow, bestCol)
    }

    private fun generateMoves(): MutableList<Array<Int>> {
        val nextMoves: MutableList<Array<Int>> = mutableListOf()

        if (WinCheck().won(state1, board) || WinCheck().won(state2, board)) {
            return nextMoves
        }

        for (row in 0 until board.ROW) {
            (0 until board.COL)
                .asSequence()
                .filter { cells[row][it]?.content == CellState.NULL }
                .forEach { nextMoves.add(arrayOf(row, it)) }
        }
        return nextMoves
    }
}