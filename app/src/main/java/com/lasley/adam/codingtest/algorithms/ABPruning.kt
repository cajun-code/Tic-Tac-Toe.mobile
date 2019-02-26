package com.lasley.adam.codingtest.algorithms

import com.lasley.adam.codingtest.Objects.Board
import com.lasley.adam.codingtest.Objects.BoardSolver
import com.lasley.adam.codingtest.Objects.Tuple2

class ABPruning(var Player: Int, val maxDepth: Int = 4, val maxPlayers: Int = 2) : AIContract {

    private var ABScore = 10

    override fun getMove(board: Board): Int {
        ABScore = board.boardPoints.size
        val gg = abPruning(board, Tuple2(Int.MIN_VALUE, Int.MAX_VALUE), Player, 0)
        return 0
    }

    // returns: Move, score
    private fun abPruning(board: Board, ab: Tuple2<Int, Int>, player: Int, depth: Int, bestMove: Int = -1)
            : Pair<Int, Int> {
        if ((depth + 1) == maxDepth)
            return Pair(bestMove, score(board, player, depth + 1))

        return if (player == Player)
            getMax(board, ab, player, depth + 1, bestMove)
        else
            getMin(board, ab, player, depth + 1, bestMove)
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
        if (value + 1 >= maxPlayers)
            return 0
        return value + 1
    }

    private fun getMax(board: Board, ab: Tuple2<Int, Int>, player: Int, depth: Int, bestMove: Int = -1)
            : Pair<Int, Int> {
        var bestMoveIndex = bestMove
        val bClone = board.clone()

        val spaces = board.getOpenSpaces()

        for (item in spaces) {
            bClone.boardPoints[item] = player
            val score = abPruning(bClone, ab, cycleInt(player), depth, item)
            bClone.boardPoints[item] = -1

            if (score.second > ab._1) {
                ab._1 = score.second
                bestMoveIndex = item
            }
            if (ab._1 >= ab._2)
                break
        }

        return Pair(bestMoveIndex, ab._1)
    }

    private fun getMin(board: Board, ab: Tuple2<Int, Int>, player: Int, depth: Int, bestMove: Int = -1)
            : Pair<Int, Int> {
        var bestMoveIndex = bestMove
        val bClone = board.clone()

        val spaces = board.getOpenSpaces()

        for (item in spaces) {
            bClone.boardPoints[item] = player
            val score = abPruning(bClone, ab, cycleInt(player), depth, bestMoveIndex)
            bClone.boardPoints[item] = -1

            if (score.second < ab._2) {
                ab._2 = score.second
                bestMoveIndex = item
            }
            if (ab._1 >= ab._2)
                break
        }

        return Pair(bestMoveIndex, ab._2)
    }

    private fun score(board: Board, player: Int, depth: Int): Int {
        if (BoardSolver(board).checkHasWinner()) {
            return if (player == Player)
                ABScore - depth
            else
                depth - ABScore
        }
        return 0
    }
}