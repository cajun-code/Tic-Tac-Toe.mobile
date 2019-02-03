//
//  Opponent.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import Foundation

class Opponent {
    static let shared = Opponent()
    
    var player: String {
        return CellOwner.opponent.rawValue
    }
    
    var opponent: String {
        return CellOwner.player.rawValue
    }
    
    var none: String {
        return CellOwner.none.rawValue
    }
    
    
    func findBestMove(_ board: inout [[String]]) -> Move {
        var bestVal = -1000;
        var bestMove = Move()
        
        for i in 0...2 {
            for j in 0...2 {
                guard board[i][j] == " " else {
                    continue
                }
                
                board[i][j] = player
                let moveVal = minimax(&board, isMaximizer: false)
                board[i][j] = " "
                
                if (moveVal > bestVal) {
                    bestMove.row = i
                    bestMove.col = j
                    bestVal = moveVal
                }
                
            }
        }
        
        return bestMove
    }
    
    func whoWon(board: [[String]]) -> String? {
        let result = evaluate(board)
        switch result {
        case 10:
            return player
        case -10:
            return opponent
        case 0:
            return nil
        default: break
        }
        return nil
    }
    
    private func minimax(_ board: inout [[String]], isMaximizer isMax: Bool) -> Int {
        let score = evaluate(board)
        
        guard score == 0 else {
            return score
        }
        
        if anyMovesLeft(board) == false { return 0 }
        
        if isMax {
            var best = -1000
            
            for i in 0...2 {
                for j in 0...2 {
                    if board[i][j] == " " {
                        board[i][j] = player
                        best = max(best,
                                   minimax(&board, isMaximizer: !isMax)
                        )
                        board[i][j] = " "
                    }
                }
            }
            
            return best
        } else {
            var best = 1000
            
            for i in 0...2 {
                for j in 0...2 {
                    if board[i][j] == " " {
                        board[i][j] = opponent
                        best = min(best,
                                   minimax(&board, isMaximizer: !isMax)
                        )
                        board[i][j] = " "
                    }
                }
            }
            
            return best
        }
    }
    
    func anyMovesLeft(_ board: [[String]]) -> Bool {
        for i in 0...2 {
            for j in 0...2 {
                if board[i][j] == none {
                    return true
                }
            }
        }
        return false
    }
    
    private func evaluate(_ board: [[String]]) -> Int {
        for row in 0...2 {
            if board[row][0] == board[row][1] &&
                board[row][1] == board[row][2]
            {
                let val = board[row][0]
                guard val == none else {
                    return (val == player) ? 10 : -10
                }
            }
        }
        
        for col in 0...2 {
            if board[0][col] == board[1][col] &&
                board[1][col] == board[2][col]
            {
                let val = board[0][col]
                guard val == none else {
                    return (val == player) ? 10 : -10
                }
            }
        }
        
        // check diagonals
        
        if board[0][0] == board[1][1] &&
            board[1][1] == board[2][2]
        {
            let val = board[0][0]
            guard val == none else {
                return (val == player) ? 10 : -10
            }
        }
        
        if board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]
        {
            let val = board[0][2]
            guard val == none else {
                return (val == player) ? 10 : -10
            }
        }
        
        return 0
    }
}
