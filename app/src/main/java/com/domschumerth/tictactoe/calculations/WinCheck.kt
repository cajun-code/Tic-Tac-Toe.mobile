package com.domschumerth.tictactoe.calculations

import com.domschumerth.tictactoe.objects.Board
import com.domschumerth.tictactoe.states.CellState

class WinCheck{

    private val patterns: Array<Int> = arrayOf(
        0b111000000, 0b000111000, 0b000000111,
        0b100100100, 0b010010010, 0b001001001,
        0b100010001, 0b001010100)

    fun check(){

    }

    fun won(player: CellState, board: Board): Boolean {
        var pattern = 0b000000000
        for (row in 0 until board.ROW) {
            (0 until board.COL)
                .asSequence()
                .filter { board.cells[row][it]?.content ==player }
                .forEach { pattern = pattern or (1 shl (row * board.COL + it)) }
        }

        patterns
            .asSequence()
            .filter { (pattern and it) == it }
            .forEach { return true }

        return false

    }
    fun tie(board: Board): Boolean {
        for (row in 0 until board.ROW) {
            (0 until board.COL)
                .filter { board.cells[row][it]?.content == CellState.NULL }
                .forEach { return false }
        }
        return true
    }
}