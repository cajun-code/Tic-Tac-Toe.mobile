package com.lasley.adam.codingtest

import com.lasley.adam.codingtest.Objects.Board
import com.lasley.adam.codingtest.algorithms.ABPruning
import org.junit.Test

class ABPruningTest {

    @Test
    fun testA() {
        val board = Board(3).also {
            it.setData(0, 0, 1)
            it.setData(0, 1, 0)
            it.setData(1, 0, 1)
        }

        val pTest = ABPruning(1)

        pTest.getMove(board)
    }
}