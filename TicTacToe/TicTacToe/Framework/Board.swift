//
//  Board.swift
//  testProject11
//
//  Created by Naraharisetty, Venkat on 3/15/19.
//  Copyright © 2019 Naraharisetty, Venkat. All rights reserved.
//

import UIKit
import Foundation

class Board: NSObject {
    var boardState:[Int] =  [0, 0, 0, 0, 0, 0, 0, 0, 0]
    var winningCombinations = [[0,1,2],[3,4,5],[6,7,8],[0,3,6],[1,4,7],[2,5,8],[0,4,8],[2,4,6]]
    init(boardState: [Int] = [0, 0, 0, 0, 0, 0, 0, 0, 0]) {
        self.boardState = boardState;
    }
    
}

extension Board {
   
    
    // Function will return current Board State
    func currentBoardState() -> [Int] {
        return self.boardState
    }
    
    /**
     * Function will add amove to the board based on parameter values
     * @param value  - current player that has to make move
     * @param atPosition   - position in board  at which we have to insert value
     */
    func addMove(playerID value:Int, atPosition index:Int) {
        self.boardState[index] = value
    }
    
    /** Function will get all possible remaning moves
     * @return All remaning possible moves for user
     */
    func getAllPossibleMoves() -> [Int] {
        var temBoardState = self.boardState;
        var possibleMoves:[Int] = []
        for index in 0...8 {
            if temBoardState[index] == 0 {
                possibleMoves.append(index)
            }
        }
        return possibleMoves
    }
    
    
    /** Function will check if moves are complete in Board State
     * @param gameState- current state of board
    */
    func checkIfMovesAreComplete(gameState:Board) -> Bool{
        if !boardState.contains(0) {
            return true
        } else {
            return false
        }
    }
    
    /** Function will return count of moves completed in current game
     * @param board- current state of board
     */
    func getCountOfMovesCompleted(board:Board) -> Int {
        var tempBoard = board.boardState
        var count = 0
        for index in 0...8 {
            if tempBoard[index] != 0 {
                count += 1
            }
        }
        return count
    }
    
    /**
     * Function will check if game is won by specified user
     * @param currentUser  - current player that has to make move
     * @param gameState         the  current Tic Tac Toe board state
     * @return Boolean valie - whether Game is Won by specified User
    */
    func checkIfGameIsWon(byUser currentUser:Int, gameState: Board) -> Bool {
        for combination in self.winningCombinations {
            if (gameState.boardState[combination[0]] == currentUser && gameState.boardState[combination[0]] == gameState.boardState[combination[1]] && gameState.boardState[combination[1]] == gameState.boardState[combination[2]]) {
                return true
            }
        }
        return false
    }
    
    /**
     * Function will check if game is won by specified user
     * @param currentUser  - current player that has to make move
     * @param gameState         the  current Tic Tac Toe board state
     * @return Boolean valie - whether Game is Won by specified User
     */
    func checkIfGameIsWonUpdated(byUser currentUser:Int, gameState: Board) -> (Bool,[Int]) {
        for combination in self.winningCombinations {
            if (gameState.boardState[combination[0]] == currentUser && gameState.boardState[combination[0]] == gameState.boardState[combination[1]] && gameState.boardState[combination[1]] == gameState.boardState[combination[2]]) {
                return (true,combination)
            }
        }
        return (false,[])
    }
    
    /**
     * Function will add Move to the board based on parameter values
     * @param currentPlayer  - current player that has to make move
     * @param atPosition   - position in board  at which we have to insert value
     */
    func addMove(currentPlayer value:Int, atPosition index:Int) {
        self.boardState[index] = value
    }
    
    /**
     * Function will remove a move from current board game
     * @param atPosition   - position in board  at which we have to remove value
     */
    func removeMove(fromPosition index:Int) {
        self.boardState[index] = 0
    }
    
    /**
     * This Function will return the value in a give index in game
     * @param atPosition   - position in board  at which we have to check for value
     * @return ->  Board Value at given index
     */
    func getMove(atPosition index:Int) -> Int{
        return self.boardState[index]
    }
    
    // This Function will reset the Board State
    func resetState(){
        self.boardState = [0, 0, 0, 0, 0, 0, 0, 0, 0]
    }
    
}
