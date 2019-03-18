package com.domschumerth.tictactoe.objects

import com.domschumerth.tictactoe.states.CellState

class Cell(val row:Int, val col:Int) {
    var content: CellState = CellState.NULL
    fun clear() {
        content = CellState.NULL
    }
}