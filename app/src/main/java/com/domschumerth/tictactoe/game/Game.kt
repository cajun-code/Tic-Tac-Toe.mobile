package com.domschumerth.tictactoe.game

import com.domschumerth.tictactoe.calculations.AI
import com.domschumerth.tictactoe.game.GameMaster.OnGameStatusChange
import com.domschumerth.tictactoe.objects.Cell
import com.domschumerth.tictactoe.states.CellState
import com.domschumerth.tictactoe.states.GameState

class Game(viewScreen: BoardView) {
    val gm: GameMaster = GameMaster()
    private val ai = AI(gm.board)
    val view = viewScreen

    var side = CellState.X
    init {
        gm.setOnGameStatusChangeListener(object : OnGameStatusChange {
            override fun onStatusChange(gameState: GameState) {
                view.gameStatus(gameState)
            }
        })
    }

    fun selectPlayer(state: CellState) {
        ai.setState(when (state) {
            CellState.X -> {
                side = CellState.X
                CellState.O
            }
            else -> {
                side = CellState.O
                CellState.X
            }
        })

        view.initGame()
    }

    fun initGame() {
        gm.initGame(side)
    }

    fun playerMove(row: Int, col: Int) {
        view.reloadBoard(gm.playerMove(ai.state2, row, col))
    }

    fun aiMove() {
        val moves = ai.move()
        view.reloadBoard(gm.playerMove(ai.state1, moves!![0], moves[1]))
        view.onComputerMoved()
    }

    interface BoardView {
        fun reloadBoard(cell: Cell?)
        fun gameStatus(gameState: GameState)
        fun initGame()
        fun onComputerMoved()
    }
}