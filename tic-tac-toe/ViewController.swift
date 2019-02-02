//
//  ViewController.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    var player = "x"
    var opponent = "o"
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        
        var board = [
            ["o", " ", " "],
            [" ", "x", " "],
            [" ", " ", "o"]
        ]
        
        let bestMove = findBestMove(&board)
        
        print(bestMove)
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
    
    func minimax(_ board: inout [[String]], isMaximizer isMax: Bool) -> Int {
        let score = evaluate(board)
        
        guard score == 0 else {
            return score
        }
        
        if anyMovesLeft(board) { return 0 }
        
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
        return false
    }
    
    func evaluate(_ board: [[String]]) -> Int {
        for row in 0...2 {
            if board[row][0] == board[row][1] &&
                board[row][1] == board[row][2]
            {
                return (board[row][0] == player) ? 10 : -10
            }
        }
        
        for col in 0...2 {
            if board[0][col] == board[1][col] &&
                board[1][col] == board[2][col]
            {
                return (board[0][col] == player) ? 10 : -10
            }
        }
        
        // check diagonals
        
        if board[0][0] == board[1][1] &&
             board[1][1] == board[2][2]
        {
            return (board[0][0] == player) ? 10 : -10
        }
        
        if board[0][2] == board[1][1] &&
            board[1][1] == board[2][0]
        {
            return (board[0][2] == player) ? 10 : -10
        }
        
        return 0
    }
}
