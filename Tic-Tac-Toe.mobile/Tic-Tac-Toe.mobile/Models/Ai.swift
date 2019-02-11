//
//  Ai.swift
//  Tic-Tac-Toe.mobile
//
//  Created by Akshay Pimprikar on 2/7/19.
//  Copyright © 2019 Akshay Pimprikar. All rights reserved.
//

import Foundation

class Ai: Player {

    func findBestMove(_ board: Board) -> Int {
        var bestEval = Int.min
        var bestMove = -1

        for move in board.availableMoves() {
            let result = minimax(board.makeMove(move), maximizing: false)
            if result > bestEval {
                bestEval = result
                bestMove = move
            }
        }
        return bestMove
    }

    func minimax(_ newBoard: Board, maximizing: Bool) -> Int {
        if newBoard.gameStatus() == .Won {
            if newBoard.currentTurn == playerType?.rawValue {
                return -1
            } else {
                return 1
            }
        } else if newBoard.gameStatus() == .Draw {
            return 0
        }

        if maximizing {
            var bestEval = Int.min
            for move in newBoard.availableMoves() {
                let result = minimax(newBoard.makeMove(move), maximizing: false)
                bestEval = max(result, bestEval)
            }
            return bestEval
        } else {
            var worstEval = Int.max
            for move in newBoard.availableMoves() {
                let result = minimax(newBoard.makeMove(move), maximizing: true)
                worstEval = min(result, worstEval)
            }
            return worstEval
        }
    }
}
