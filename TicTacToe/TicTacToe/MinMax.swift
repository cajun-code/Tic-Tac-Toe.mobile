//
//  MinMax.swift
//  TicTacToe
//
//  Created by Mingu Chu on 2/16/19.
//  Copyright © 2019 Mike Chu. All rights reserved.
//

import UIKit

enum Player : Int {
    case human = 1
    case computer = 2
}

enum GameStatus {
    case win
    case lose
    case tie
    case inProgress
}

class TicTacToeAI: NSObject {

    private func minMax(game:GameBoard, player: Int, depth: Int) -> [Int] {
        
        let availableMoves = game.getAvailableMoves()
        let status = game.checkGameStatus()
        var bestMove = -1
        var bestScore = player == Player.computer.rawValue ? Int.min : Int.max
        var score: Int
        
        if status != GameStatus.inProgress || availableMoves.count == 0 {
            return evaluateScore(status: status, depth: depth, bestMove: bestMove)
        }
        
        for move in availableMoves {
            let nextGameState = GameBoard(boardState: game.getBoardState())
            nextGameState.addMove(player: player, onPosition: move)
            
            if player == Player.computer.rawValue {
                score = minMax(game: nextGameState, player: Player.human.rawValue, depth: depth + 1)[0]
                
                if score > bestScore {
                    bestScore = score
                    bestMove = move
                }
            } else {
                score = minMax(game: nextGameState, player: Player.computer.rawValue, depth: depth + 1)[0]
                
                if score < bestScore {
                    bestScore = score
                    bestMove = move
                }
            }
        }
        return [bestScore, bestMove]
    }
    
    private func evaluateScore(status: GameStatus, depth: Int, bestMove: Int) -> [Int] {
        
        if status == GameStatus.win {
            return [depth - 10, bestMove]
        } else {
            
            if status == GameStatus.lose {
                return [10 - depth, bestMove]
            } else {
                return [0, bestMove]
            }
        }
    }
    
    func nextMove(board: GameBoard, player: Int) -> Int{
        return minMax(game: board, player: player, depth: 0)[1]
    }
    
    
    
}
