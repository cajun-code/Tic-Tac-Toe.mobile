//
//  AIEngine.swift
//  testProject11
//
//  Created by Naraharisetty, Venkat on 3/15/19.
//  Copyright © 2019 Naraharisetty, Venkat. All rights reserved.
//

import UIKit
import Foundation

class AIEngine: NSObject {
    var board:Board = Board()
}


extension AIEngine{
    
    // Function getting called from GameController when computers turn
    func getAIsMove(board:Board, player:Int) -> Int {
        if board.getMove(atPosition: 4) == 0 {
            return 4
        }
        let checkForGameEnd = checkForGameEndChance(board:board)
        if checkForGameEnd.0 {
            return checkForGameEnd.1
        }
        return miniMaxUpdated(currentGameState: board, isMaximizingPLayer: true, alpha: Int.min, beta: Int.max)[1]
       
    
    }
    
    // Function to count score and update ot minmax algorithm
    func countScore(gameState:Board,bestMove: Int) ->[Int] {
        if board.checkIfGameIsWon(byUser: 2, gameState:gameState) {
            return [1,bestMove]
        } else if board.checkIfGameIsWon(byUser: 1, gameState:gameState){
            return [-1,bestMove]
        } else {
            return [0,bestMove]
        }
    }
    
    
    // Min Max Algoritm with alpha beta pruning. Core algorithm to predict next correct move of a computer
    func miniMaxUpdated(currentGameState:Board, isMaximizingPLayer:Bool, alpha:Int,beta:Int) -> [Int] {
        var alpha = alpha
        var beta = beta
        var bestMove = -1
        if currentGameState.checkIfMovesAreComplete(gameState: currentGameState){
            return countScore(gameState:currentGameState, bestMove:bestMove)
        }
        let possibleMoves = currentGameState.getAllPossibleMoves()
        let gameStateTemp:Board = Board(boardState: currentGameState.currentBoardState())
        
        if isMaximizingPLayer { // Maximizing scenario
            var  bestValue = Int.min
            for move in possibleMoves{
                gameStateTemp.addMove(playerID: 2, atPosition: move)
                let returnValue = miniMaxUpdated(currentGameState: gameStateTemp, isMaximizingPLayer: false, alpha: alpha, beta: beta)[0]
                if returnValue > bestValue{
                    bestMove = move
                    bestValue = returnValue
                }
                gameStateTemp.removeMove(fromPosition: bestMove)
                
                // alpha beta
                alpha = max(alpha,bestValue)
                if (bestValue > beta){
                    return [bestValue,bestMove]
                }
                // alpha beta
            }
            return [bestValue,bestMove]
        } else { // Minimising scenario
            var  bestValue = Int.max
            
            for move in possibleMoves{
                gameStateTemp.addMove(playerID: 1, atPosition: move)
                let returnValue  = miniMaxUpdated(currentGameState: gameStateTemp, isMaximizingPLayer: true, alpha: alpha, beta: beta)[0]
                if returnValue < bestValue {
                    bestMove = move
                    bestValue = returnValue
                }
                gameStateTemp.removeMove(fromPosition: bestMove)
                
                //  alpha beta
                beta = min(beta,bestValue)
                if beta <= alpha {
                    return [bestValue,bestMove]
                }
                // alpha beta
            }
            return [bestValue,bestMove]
        }
    }
    
    
    // Function to chekc if there are chances for a game to end. Will check if any row has 2 valid value of a user
    func checkForGameEndChance(board:Board) -> (Bool,Int) {
        var willGameEnd:Bool = false
        var correctMove:Int = 1
        for combination in board.winningCombinations {
            if ((board.currentBoardState()[combination[0]] == board.currentBoardState()[combination[1]] ) && board.currentBoardState()[combination[2]] == 0 && board.currentBoardState()[combination[0]] != 0 )
                || ((board.currentBoardState()[combination[0]] == board.currentBoardState()[combination[2]]) && board.currentBoardState()[combination[1]] == 0 && board.currentBoardState()[combination[0]] != 0 )
                || ((board.currentBoardState()[combination[1]] == board.currentBoardState()[combination[2]]) && board.currentBoardState()[combination[0]] == 0  && board.currentBoardState()[combination[1]] != 0  ){
                willGameEnd = true
                if ((board.currentBoardState()[combination[0]] == board.currentBoardState()[combination[1]]) && board.currentBoardState()[combination[2]] == 0){
                    correctMove = combination[2]
                } else if ((board.currentBoardState()[combination[0]] == board.currentBoardState()[combination[2]]) && board.currentBoardState()[combination[1]] == 0) {
                    correctMove = combination[1]
                } else if ((board.currentBoardState()[combination[1]] == board.currentBoardState()[combination[2]]) && board.currentBoardState()[combination[0]] == 0) {
                    correctMove = combination[0]
                }
                return (willGameEnd,correctMove)
            }
        }
        return (willGameEnd,correctMove)
    }
    
    
    
    
}

