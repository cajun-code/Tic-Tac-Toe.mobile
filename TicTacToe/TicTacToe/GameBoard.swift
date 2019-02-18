//
//  GameBoard.swift
//  TicTacToe
//
//  Created by Mingu Chu on 2/16/19.
//  Copyright © 2019 Mike Chu. All rights reserved.
//

import Foundation

class GameBoard: NSObject {
    
    let cleanBoardState = [0, 0, 0, 0, 0, 0, 0, 0, 0]
    
    var boardState: [Int]
    
    var winningStates = [[0,1,2],
                         [3,4,5],
                         [6,7,8],
                         [0,3,6],
                         [1,4,7],
                         [2,5,8],
                         [0,4,8],
                         [2,4,6]]
    
    init(boardState: [Int] = [0, 0, 0, 0, 0, 0, 0, 0, 0]) {
        self.boardState = boardState;
    }
    
    
    
    
    func getBoardState() -> [Int] {
        return self.boardState
    }
    
    func resetBoardState() {
        self.boardState = cleanBoardState
    }
    
    func checkGameStatus() -> GameStatus {
        for winnerState in winningStates {
            
            if  boardState[winnerState[0]] != 0 &&
                boardState[winnerState[0]] == boardState[winnerState[1]] &&
                boardState[winnerState[1]] == boardState[winnerState[2]] {
                
                if boardState[winnerState[0]] == 1 {
                    return GameStatus.win
                } else {
                    return GameStatus.lose
                }
                
            }
        }
        
        if !boardState.contains(0) {
            return GameStatus.tie
        }
        
        return GameStatus.inProgress
    }
    
    
    
    func addMove(player: Int!, onPosition: Int!) {
        self.boardState[onPosition] = player
    }
    
    func getMove(onPosition:Int!) -> Int {
        return self.boardState[onPosition]
    }
    
    func getAvailableMoves() -> [Int] {
        var stateCopy = self.boardState
        var availableMoves:[Int] = []
        var index = stateCopy.firstIndex(of: 0)
        
        while index != nil {
            availableMoves.append(index!)
            stateCopy[index!] = -1
            index = stateCopy.firstIndex(of: 0)
        }
        
        return availableMoves
    }
    

    

    
    
}

