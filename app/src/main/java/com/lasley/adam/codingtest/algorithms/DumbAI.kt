package com.lasley.adam.codingtest.algorithms

import com.lasley.adam.codingtest.Objects.Board

/**
 * Bare minimum AI which puts a place in a random location on the board
 */
class DumbAI : AIContract {
    override fun getMove(board: Board) = board.getOpenSpaces().random()
}