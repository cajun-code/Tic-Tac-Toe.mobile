package com.example.tictactoe

class Game(player: Char) {

    var currentPlayer: Int? = null
    val playerPiece = player
    var board = IntArray(9)
    val maxNumMoves = 9

}
