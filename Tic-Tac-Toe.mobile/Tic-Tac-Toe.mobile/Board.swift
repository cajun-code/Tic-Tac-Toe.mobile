//
//  Board.swift
//  Tic-Tac-Toe.mobile
//
//  Created by Akshay Pimprikar on 2/8/19.
//  Copyright © 2019 Akshay Pimprikar. All rights reserved.
//

import Foundation

enum gameState {
    case InProgress
    case Won
    case Draw
}

class Board {
    public var gameBoardArray = Array(repeating: "", count: 9)
    var currentTurn = ""

    func availableMoves() -> [Int] {
        return gameBoardArray.indices.filter({ gameBoardArray[$0] == "" })
    }

    func gameStatus() -> gameState {

        var winningCombination = false
        //Horizontal
        if !gameBoardArray[0].isEmpty && Set([gameBoardArray[0], gameBoardArray[1], gameBoardArray[2]]).count == 1 || !gameBoardArray[3].isEmpty && Set([gameBoardArray[3], gameBoardArray[4], gameBoardArray[5]]).count == 1 || !gameBoardArray[6].isEmpty && Set([gameBoardArray[6], gameBoardArray[7], gameBoardArray[8]]).count == 1 {
            winningCombination = true
        }

        //Vertical
        if !gameBoardArray[0].isEmpty && Set([gameBoardArray[0], gameBoardArray[3], gameBoardArray[6]]).count == 1 || !gameBoardArray[1].isEmpty && Set([gameBoardArray[1], gameBoardArray[4], gameBoardArray[7]]).count == 1 || !gameBoardArray[2].isEmpty && Set([gameBoardArray[2], gameBoardArray[5], gameBoardArray[8]]).count == 1 {
            winningCombination = true
        }

        //Diagonals
        if !gameBoardArray[0].isEmpty && Set([gameBoardArray[0], gameBoardArray[4], gameBoardArray[8]]).count == 1 || !gameBoardArray[2].isEmpty && Set([gameBoardArray[2], gameBoardArray[4], gameBoardArray[6]]).count == 1  {
            winningCombination = true
        }

        if winningCombination {
            return .Won
        }

        //Draw
        if gameBoardArray.filter({ $0.isEmpty }).count == 0 {
            return .Draw
        }
        
        return .InProgress
    }

    func makeMove(_ move: Int) -> Board {
        let newBoard = Board()
        newBoard.gameBoardArray = gameBoardArray
        newBoard.gameBoardArray[move] = currentTurn
        newBoard.currentTurn = togglePlayer()
        return newBoard
    }

    func togglePlayer() -> String {
        if currentTurn == PlayerType.O.rawValue {
            return PlayerType.X.rawValue
        } else {
            return PlayerType.O.rawValue
        }
    }
}
