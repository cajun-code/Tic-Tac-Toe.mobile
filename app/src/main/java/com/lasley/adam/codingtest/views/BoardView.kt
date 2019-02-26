package com.lasley.adam.codingtest.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.lasley.adam.codingtest.Objects.Board
import com.lasley.adam.codingtest.Objects.BoardSolver
import com.lasley.adam.codingtest.Objects.GamePrefs
import com.lasley.adam.codingtest.algorithms.AIContract
import com.lasley.adam.codingtest.algorithms.DumbAI


class BoardView : View {
    private var Bwidth: Int = 0
    private var Bheight: Int = 0
    private var eltW: Int = 0
    private var eltH: Int = 0
    private var gridPaint: Paint = Paint()
    private var Paint_Player1: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var Paint_AI: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var board: Board? = null
    private var BoardSize = 3

    var Turn = MutableLiveData(1)
    var GameWinner = MutableLiveData(0)
    lateinit var bSolver: BoardSolver
    var AIPlayer: AIContract = DumbAI()

    companion object {
        private const val LINE_THICK = 5
        //        private const val ELT_MARGIN = 20
        private const val ELT_STROKE_WIDTH = 15
        const val GAME_Tie = 100
    }

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        BoardSize = 3 + (if (isInEditMode) 0 else GamePrefs().boardSize)
        Paint_Player1.color = Color.BLUE
        Paint_Player1.style = Paint.Style.STROKE
        Paint_Player1.strokeWidth = ELT_STROKE_WIDTH.toFloat()

        Paint_AI.color = Color.RED
        Paint_AI.style = Paint.Style.STROKE
        Paint_AI.strokeWidth = ELT_STROKE_WIDTH.toFloat()
    }

    fun setBoard(b: Board) {
        board = b
        bSolver = BoardSolver(
            b, 2 + (if (isInEditMode) 1 else GamePrefs().winSize)
        )
    }

    fun ResetBoard() {
        board?.ClearBoard()
        GameWinner.value = 0
        Turn.value = 1
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Bheight = View.MeasureSpec.getSize(heightMeasureSpec)
        Bwidth = View.MeasureSpec.getSize(widthMeasureSpec)
        eltW = (Bwidth - LINE_THICK) / BoardSize
        eltH = (Bheight - LINE_THICK) / BoardSize

        Paint_Player1.textSize = Math.min(eltW, eltH).toFloat()
        Paint_Player1.typeface = Typeface.MONOSPACE

        Paint_AI.textSize = Paint_Player1.textSize
        Paint_AI.typeface = Paint_Player1.typeface

        setMeasuredDimension(Bwidth, Bheight)
    }

    override fun onDraw(canvas: Canvas) {
        gridPaint.color = Color.BLACK
        canvas.drawRect(0f, 0f, Bwidth - 1f, Bheight - 1f, gridPaint)
        gridPaint.color = Color.WHITE
        canvas.drawRect(1f, 1f, Bwidth - 2f, Bheight - 2f, gridPaint)
        gridPaint.color = Color.BLACK

        drawGrid(canvas)
        drawBoard(canvas)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (GameWinner.value != 0 || Turn.value ?: 0 < 0)
            return true

        if (event.action == MotionEvent.ACTION_DOWN)
            return true

        if (event.action == MotionEvent.ACTION_UP) {
            if (preMark != 200) {
                board?.setData(movePx, movePy, preMark)
                preMark = 200
                invalidate()
                return true
            }
            val x = (event.x / eltW).toInt()
            val y = (event.y / eltH).toInt()
            board?.let { playerAction(it, x, y) }
            return true
        }

        // BuildConfig.DEBUG
        if (false && event.action == MotionEvent.ACTION_MOVE) {
            board?.let {
                if (preMark != 200)
                    it.setData(movePx, movePy, preMark)
                movePx = (event.x / eltW).toInt()
                movePy = (event.y / eltH).toInt()

                preMark = it.getData(movePx, movePy)
                it.setData(movePx, movePy, 5)
                invalidate()
                return true
            }
        }

        return super.onTouchEvent(event)
    }

    var gg = false

    var preMark = 200
    var movePx = -1
    var movePy = -1

    private fun playerAction(b: Board, x: Int, y: Int) {
        if ((b.getData(x, y) and Board.PlayerMask) > 0)
            return// clicked on a played piece, no action

        b.setData(x, y, Turn.value ?: 1)
        invalidate()

        if (bSolver.fastIsWin((x + (y * b.BoardSize)))) {
            GameWinner.value = Turn.value
            return// game over
        } else if (b.getOpenSpaces().isEmpty()) {
            GameWinner.value = GAME_Tie
            return// tie
        }

        Turn.value = (Turn.value ?: 1) + 1
        if ((Turn.value ?: 1) <= GamePrefs().PlayerCount)
            return// Next player's turn

        // switch to AI's turn
        Turn.value = -1

        while (Math.abs((Turn.value ?: 0)) <= GamePrefs().AICount) {
            val AIMove = AIPlayer.getMove(b)
            b.boardPoints[AIMove] = Turn.value ?: -1
            invalidate()
            if (bSolver.fastIsWin(AIMove)) {
                GameWinner.value = Turn.value ?: -1
                return// game over
            }

            if (b.getOpenSpaces().isEmpty()) {
                GameWinner.value = GAME_Tie
                return// tie
            }
            // Next AI
            Turn.value = (Turn.value ?: -1) - 1
        }

        // return to player 1
        Turn.value = 1
    }

    private fun drawGrid(canvas: Canvas) {
        for (i in 0..(BoardSize - 2)) {
            // vertical lines
            val left = (eltW * (i + 1)).toFloat()
            val right = left + LINE_THICK
            val top = 0f
            val bottom = Bheight.toFloat()

            canvas.drawRect(left, top, right, bottom, gridPaint)

            // horizontal lines
            val left2 = 0f
            val right2 = Bwidth.toFloat()
            val top2 = (eltH * (i + 1)).toFloat()
            val bottom2 = top2 + LINE_THICK

            canvas.drawRect(left2, top2, right2, bottom2, gridPaint)
        }
    }

    private fun drawBoard(canvas: Canvas) {
        var mPaint = Paint()

        board?.let {
            for (xx in 0..(it.BoardSize - 1)) {
                for (yy in 0..(it.BoardSize - 1)) {
                    val mark = it.boardPoints[xx + (yy * it.BoardSize)]

                    val markChar = when (mark) {
                        1 -> {
                            mPaint = Paint_Player1
                            if (GamePrefs().playerX) "X" else "O"
                        }
                        -1 -> {
                            mPaint = Paint_AI
                            if (GamePrefs().playerX) "O" else "X"
                        }
                        5 -> {
                            mPaint = Paint_Player1
                            "+"
                        }
                        else -> ""
                    }

                    if (markChar.isNotEmpty())
                        canvas.drawText(
                            markChar,
                            ((eltW * xx) + (eltW / 4)).toFloat(),
                            ((eltH * (yy + 1)) - (eltH / 8)).toFloat(), mPaint
                        )
                }
            }
        }
    }
}