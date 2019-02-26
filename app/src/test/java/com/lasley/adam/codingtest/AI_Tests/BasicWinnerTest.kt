package com.lasley.adam.codingtest.AI_Tests

import com.lasley.adam.codingtest.Objects.Board
import com.lasley.adam.codingtest.Objects.BoardSolver
import com.lasley.adam.codingtest.algorithms.BasicWinner
import org.junit.Assert
import org.junit.Test

class BasicWinnerTest {

    @Test
    fun testWinningMove() {
        val board = Board(4).also {
            it.boardPoints[8] = 1
            it.boardPoints[9] = 1
        }
        val ai = BasicWinner()
        ai.boardSolver = BoardSolver(board)

        val move = ai.findWinningMove(board, 1)
        Assert.assertEquals(move, 10)
    }

    @Test
    fun testWinningMove_b() {
        val board = Board(4)
        val ai = BasicWinner()
        ai.boardSolver = BoardSolver(board)

        board.boardPoints[4] = 1
        board.boardPoints[5] = 1
        var move = ai.findWinningMove(board, 1)
        Assert.assertEquals(move, 6)


        board.boardPoints[6] = -1
        board.boardPoints[9] = 1
        board.boardPoints[15] = -1
        /*
        * | * | * | *
        X | X | o | *
        * | X | * | *
        * | * | * | O
        */
        move = ai.findWinningMove(board, 1)
        Assert.assertEquals(move, 1)

    }
}