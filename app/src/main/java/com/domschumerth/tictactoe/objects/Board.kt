package com.domschumerth.tictactoe.objects

import com.domschumerth.tictactoe.utils.array2d

class Board {

    val ROW = 3
    val COL = 3
    var currentRow = 0
    var currentCol = 0

    var cells = array2d<Cell?>(ROW, COL) { null }



    init {
        for (row in 0 until ROW) {
            for (col in 0 until COL) {
                cells[row][col] = Cell(row, col)
            }
        }
    }

    fun init() {
        for (row in 0 until ROW) {
            for (col in 0 until COL) {
                cells[row][col]?.clear()
            }
        }
    }
}