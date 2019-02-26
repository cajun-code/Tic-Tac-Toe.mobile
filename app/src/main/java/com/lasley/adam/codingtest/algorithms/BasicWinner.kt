package com.lasley.adam.codingtest.algorithms

import com.lasley.adam.codingtest.Objects.Board
import com.lasley.adam.codingtest.Objects.BoardSolver
import com.lasley.adam.codingtest.Objects.GamePrefs

/* Move importance
          1. winning move
          2. block player's winning move
          3. move on corner
          4. move on center
          5. move on side
          */
class BasicWinner(val ID: Int = -1) : AIContract {
    lateinit var boardSolver: BoardSolver


    override fun getMove(board: Board): Int {
        boardSolver = BoardSolver(board)

        // next winning move for AI
        var nextMove = findWinningMove(board, ID)
        if (nextMove != Board.InvalidMark) return nextMove

        // block the enemy's move
        for (it in 1..GamePrefs().PlayerCount) {
            nextMove = findWinningMove(board, it)
            if (nextMove != Board.InvalidMark) return nextMove
        }

        // try to get a corner piece
        nextMove = emptyCorner(board)
        if (nextMove != Board.InvalidMark) return nextMove

        // try to get a center
        nextMove = getCenter(board)
        if (nextMove != Board.InvalidMark) return nextMove

        // get any remaining open space
        return board.getOpenSpaces().random()
    }

    /**
     * Cycles the in value through the max number of players
     *
     *  == max = 2 ==
     * 0 -> 1
     * 1 -> 0
     *
     * == max = 3 ==
     * 0 -> 1
     * 1 -> 2
     * 2 -> 0
     */
    private fun cycleInt(value: Int): Int {
        if (value + 1 >= GamePrefs().AICount)
            return 0
        return value + 1
    }

    fun findWinningMove(board: Board, cPlayer: Int): Int {
        val pSpaces = board.getPlayerSpaces(cPlayer)
        if (pSpaces.size < 2) return Board.InvalidMark// not enough marks to win with

        var winningSpace = Board.InvalidMark
        pSpaces.any markCheck@{ p ->
            board.emptyAroundLoc(p).any emptyCheck@{ e ->
                if (boardSolver.fastIsWin(e, cPlayer)) {
                    winningSpace = e
                    return@emptyCheck true
                }
                return@emptyCheck false
            }
            winningSpace != Board.InvalidMark
        }

        return winningSpace
    }

    private fun emptyCorner(board: Board): Int {
        val corners = arrayOf(
            0, board.BoardSize - 1,
            board.BoardSize * (board.BoardSize - 1),
            (board.BoardSize * board.BoardSize) - 1
        ).filter { board.boardPoints[it] == Board.EmptyMark }

        if (corners.isEmpty()) return Board.InvalidMark
        return corners.random()
    }

    private fun getCenter(board: Board): Int {
        val bPoints = board.getOpenSpaces()

        val top = (0..(board.BoardSize - 1))
        val left = top.map { it * board.BoardSize }
        val right = left.map { it + (board.BoardSize - 1) }
        val bottom = top.map { (board.BoardSize * (board.BoardSize - 1)) + it }

        val fPoints = bPoints.filter {
            !(top.contains(it) || left.contains(it) || right.contains(it) || bottom.contains(it))
        }

        if (fPoints.isEmpty()) return Board.InvalidMark
        return fPoints.random()
    }
}