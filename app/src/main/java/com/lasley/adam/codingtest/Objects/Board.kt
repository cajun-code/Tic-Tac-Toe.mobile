package com.lasley.adam.codingtest.Objects

import kotlin.math.abs
import kotlin.math.sign

class Board {
    enum class directions(val x: Int, val y: Int) {
        None(0, 0),
        NW(-1, -1), N(0, -1), NE(1, -1),
        W(-1, 0), E(1, 0),
        SW(-1, 1), S(0, 1), SE(1, 1);

        companion object {
            fun toDir(x: Int, y: Int): directions {
                if (abs(x) > 1 || abs(y) > 1)
                    return directions.None
                return directions.values().first {
                    (x.sign == it.x.sign) && (y.sign == it.y.sign)
                }
            }
        }
    }

    /**
     * Board data in array format.
     * 0: nothing
     * 1: player 1
     * 2: player 2 (AI)
     */
    var boardPoints = intArrayOf(0)
//        private set

    private var _boardSize = 1
    /**
     * The size (single x/y direction) of this board
     */
    var BoardSize
        get() = _boardSize
        set(value) {
            if (value > 2) {
                _boardSize = value
                boardPoints = IntArray(value * value) { _ -> EmptyMark }
            }
        }

    companion object {
        const val EmptyMark = 16
        const val InvalidMark = 32
        const val PlayerMask = 15
        const val NonPlayerMask = 48
    }

    fun clone(): Board = Board().also {
        it._boardSize = _boardSize
        it.boardPoints = boardPoints.copyOf()
    }

    constructor(size: Int = 3) {
        BoardSize = size
    }

    fun getData(X: Int, Y: Int): Int {
        if ((X + (Y * _boardSize)) > boardPoints.size)
            return InvalidMark
        return boardPoints[X + (Y * _boardSize)]
    }

    fun setData(X: Int, Y: Int, S: Int) {
        if ((X + (Y * _boardSize)) <= boardPoints.size)
            boardPoints[X + (Y * _boardSize)] = S
    }

    fun ClearBoard() {
        (0..(boardPoints.size - 1)).forEach {
            boardPoints[it] = EmptyMark
        }
    }

    // Index converted to board-X coord
    private val Int.bX
        get() = this % BoardSize

    // Index converted to board-Y coord
    private val Int.bY
        get() = (this - (this % BoardSize)) / BoardSize

    /**
     * Angle from SpaceA to SpaceB
     */
    fun getAngle(spaceA: Int, spaceB: Int) = directions.toDir(
        spaceB.bX - spaceA.bX, spaceB.bY - spaceA.bY
    )

    /**
     * Returns the next space in the line of the direction
     */
    fun getSpace(next: Int, dir: directions, mult: Int = 1): Int {
        val x = next.bX + (dir.x * mult)
        val y = next.bY + (dir.y * mult)

        if ((x < 0 || x >= _boardSize) || (y < 0 || y >= _boardSize))
            return InvalidMark
        return x + (y * _boardSize)
    }

    /**
     * Returns TRUE if there is a valid space in the following direction from Next
     */
    fun isValidMove(next: Int, dir: directions, mult: Int = 1) =
        ((next.bX + (dir.x * mult)) in 0..(_boardSize - 1)) && ((next.bY + (dir.y * mult)) in 0..(_boardSize - 1))

    /**
     * Returns player spaces by index value
     */
    fun getPlayerSpaces(player: Int) = boardPoints.withIndex()
        .filter { it.value == player }.map { it.index }

    /**
     * Returns available spaces by index value
     */
    fun getOpenSpaces() = getPlayerSpaces(EmptyMark)

    /**
     * Returns available spaces by index value surrounding the input location
     */
    fun emptyAroundLoc(location: Int) = directions.values().map map@{
        if (it == directions.None)
            return@map InvalidMark
        if (isValidMove(location, it) && boardPoints[getSpace(location, it)] == EmptyMark)
            return@map getSpace(location, it)
        return@map InvalidMark
    }.filter { it != InvalidMark }
}