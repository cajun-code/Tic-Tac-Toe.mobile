package com.lasley.adam.codingtest.algorithms

import com.lasley.adam.codingtest.Objects.Board

interface AIContract {
    fun getMove(board: Board): Int
}