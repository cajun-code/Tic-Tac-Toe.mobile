package com.lasley.adam.codingtest

import com.lasley.adam.codingtest.Objects.Board
import com.lasley.adam.codingtest.Objects.BoardSolver
import org.junit.Assert
import org.junit.Test

class BoardSolverTests {

    val board = Board(3)

    @Test
    fun testFastSolve() {
        board.BoardSize = 3
        val solver = BoardSolver(board)

        board.boardPoints[0] = 1
        board.boardPoints[1] = 1
        Assert.assertFalse(solver.fastIsWin(0))

        board.boardPoints[2] = 1
        Assert.assertTrue(solver.fastIsWin(0))
    }

    @Test
    fun testFastSolve_4() {
        board.BoardSize = 4
        val solver = BoardSolver(board)

        board.boardPoints[4] = 1
        board.boardPoints[5] = 1
        Assert.assertTrue(solver.fastIsWin(6, 1))

        board.boardPoints[6] = -1
        board.boardPoints[9] = 1
        board.boardPoints[15] = -1
        Assert.assertTrue(solver.fastIsWin(1, 1))
        Assert.assertFalse(solver.fastIsWin(3, -1))
    }
}