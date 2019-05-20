package com.example.tictactoe

enum class CellState(val value: Int) {
    BLANK(0),
    X(1),
    O(2)
}

class Game(player: Char) {

    var currentPlayer: Int? = null
    val playerPiece = CellState.valueOf(player.toString())
    var board = IntArray(9)
    val maxNumMoves = 9
    lateinit var compPiece: CellState

    fun assignComp() = if (playerPiece.equals(CellState.X)) compPiece = CellState.O else compPiece = CellState.X

}
