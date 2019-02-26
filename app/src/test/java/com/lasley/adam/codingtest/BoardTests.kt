package com.lasley.adam.codingtest

import com.lasley.adam.codingtest.Objects.Board
import org.junit.Assert
import org.junit.Test

class BoardTests {

    val board = Board(3)

    @Test
    fun test_getSpaces() {
        board.BoardSize = 3
        val aa = board.isValidMove(0, Board.directions.S, 2)
        val ab = board.getSpace(0, Board.directions.S, 2)

        Board.directions.values().forEach { d ->
            (0..1).forEach {
                Assert.assertNotEquals(
                    board.getSpace(4, d, it), Board.InvalidMark
                )
            }
        }

        Assert.assertEquals(board.getSpace(0, Board.directions.S, 3), Board.InvalidMark)

    }

    @Test
    fun test_EmptySpaces_4() {
        /* 4x4
        0 | 1 | 2 | 3
        4 | 5 | 6 | 7
        8 | 9 | 10 | 11
        12| 13| 14| 15
        */
        board.BoardSize = 4

        var spaces = board.emptyAroundLoc(8)
        Assert.assertEquals(spaces.size, 5)
        Assert.assertArrayEquals(spaces.toIntArray(), intArrayOf(4, 5, 9, 12, 13))

        spaces = board.emptyAroundLoc(9)
        Assert.assertEquals(spaces.size, 8)
        Assert.assertArrayEquals(spaces.toIntArray(), intArrayOf(4, 5, 6, 8, 10, 12, 13, 14))
    }
}