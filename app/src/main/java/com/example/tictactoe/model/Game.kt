package com.example.tictactoe.model

data class Game(
    val player: String,
    val ai: String,
    var aiMove: Move = Move(),
    val board: MutableList<Any> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
) {
    data class Move(
        var index: Int = Int.MIN_VALUE,
        var score: Int = Int.MIN_VALUE
    )
}