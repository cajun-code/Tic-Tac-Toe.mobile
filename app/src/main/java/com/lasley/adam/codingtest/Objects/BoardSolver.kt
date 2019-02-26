package com.lasley.adam.codingtest.Objects

import java.lang.Math.abs


class BoardSolver(val board: Board, val winSize: Int = 3) {

    val player = 1
    private val spaces = board.getPlayerSpaces(player)
    private val bSize = board.BoardSize

    /**
     * Check the board for if the player has won
     */
    fun checkHasWinner() = checkColums() || checkDiags()

    /**
     * Optimized function to check if the player has won
     */
    fun checkHasWinner(location: Int): Boolean {
        if (location == -1)
            return checkHasWinner()
        // limit the search to only from the input location
        val pSpaces = spaces.filter {
            if (bSize > 3) {// for larger boards, remove extra false positives
                return ((it in ((location - (bSize + 1))..(location - (bSize - 1)))) ||
                        (it in ((location - 1)..(location + 1))) ||
                        (it in ((location + (bSize - 1))..(location + (bSize + 1)))))
            }
            abs(location - it) <= (bSize + 2)
        }
        return Board.directions.values().any {
            checkDirection(board, pSpaces, it)
        }
    }

    private fun checkColums() =
        checkDirection(board, spaces, Board.directions.N) ||
                checkDirection(board, spaces, Board.directions.S) ||
                checkDirection(board, spaces, Board.directions.E) ||
                checkDirection(board, spaces, Board.directions.W)

    private fun checkDiags() =
        checkDirection(board, spaces, Board.directions.NE) ||
                checkDirection(board, spaces, Board.directions.NW) ||
                checkDirection(board, spaces, Board.directions.SE) ||
                checkDirection(board, spaces, Board.directions.SW)

    private fun checkDirection(board: Board, spaces: List<Int>, dir: Board.directions): Boolean {
        return spaces.any { s ->
            // search if any of the following is true
            (0..winSize).all { w ->
                if (dir != Board.directions.None && board.isValidMove(s, dir, w))
                    return board.getSpace(s, dir, w) == player
                return false
            }
        }
    }

    /**
     * 'quickly' checks if the (any) player at location has a winning move
     */
    fun fastIsWin(location: Int, playerType: Int? = null): Boolean {
        val locType = playerType ?: board.boardPoints[location]
        val tests = arrayOf(
            arrayOf(Board.directions.N, Board.directions.S),
            arrayOf(Board.directions.E, Board.directions.W),
            arrayOf(Board.directions.NE, Board.directions.SW),
            arrayOf(Board.directions.NW, Board.directions.SE)
        )

        var mult: Int
        var winCnt: Int
        var stopTest = 0// 0: running test, 1: hit break A, 2: hit break B, 3: hit both breaks

        if (tests.any testDirs@{ t ->
                mult = 1
                winCnt = 1
                stopTest = 0
                while (stopTest != 3) {
                    if ((stopTest and 1) == 0 &&
                        board.isValidMove(location, t[0], mult) &&
                        board.boardPoints[board.getSpace(location, t[0], mult)] == locType
                    ) {
                        winCnt++
                    } else
                        stopTest = stopTest or 1

                    if ((stopTest and 2) == 0 &&
                        board.isValidMove(location, t[1], mult) &&
                        board.boardPoints[board.getSpace(location, t[1], mult)] == locType
                    ) {
                        winCnt++
                    } else
                        stopTest = stopTest or 2

                    mult++
                }
                return@testDirs winCnt == winSize
            })
            return true
        return false
    }
}