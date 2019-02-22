//
//  ViewControllerVM.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation

protocol ViewControllerVMDelegate {
    func refreshData(indexes: [IndexPath]?)
    func setStatus(text: String)
}

class ViewControllerVM {
    //delegate to inform VC of updates of data
    var delegate: ViewControllerVMDelegate?
    //array to keep track of user selected moves
    var userSelections: [Int] = []
    //array to keep track of computer selected moves
    var computerSelections: [Int] = []
    //array to keep track of winning combination, if any
    var winningCombination: [Int] = []
    //possible indexes that win the game
    let winningCombinations = [
        [0,1,2], [3,4,5], [6,7,8],
        [0,3,6], [1,4,7], [2,5,8],
        [0,4,8], [6,4,2]
    ]
    
    //reset all data to start a new game
    func resetGame() {
        computerSelections.removeAll()
        userSelections.removeAll()
        winningCombination.removeAll()
    }
    
    //MARK: Evaluate what computer's move should be
    func beginComputerMove() {
        //pick center if its avaialble
        //center spot leads to more possibilities to win
        if !userSelections.contains(4) && !computerSelections.contains(4) {
            //computer selects center square since its the one that's open
            selectComputerMove(index: 4)
            return
        }
        
        //check if a win is available in this move
        guard !winIfPossible() else {
            return
        }
        
        //check if computer needs to block a winning move
        guard !blockIfNeeded() else {
            return
        }
        
        //continue with strategy depending on first move
        computerSelections.contains(4) ? centerStrategy() : cornerStrategy()
    }
    
    //MARK: Strategies
    func centerStrategy() {
        //this will be computer's second move, first was selecting center
        guard !(userSelections.contains(0) && userSelections.contains(8)) && !(userSelections.contains(2) && userSelections.contains(6)) else {
            //if user selected 2 corners, diagonally, pick the edges, otherwise user can have 2 ways of winning
            selectEdgeIndex()
            return
        }
        //ran out of edges
        selectComputerMoveRandomly()
    }
    
    func cornerStrategy() {
        let corners = [0,2,6,8].shuffled()
        for corner in corners {
            guard !selectComputerMove(index: corner) else {
                //move completed
                return
            }
        }
        //ran out of corners
        selectComputerMoveRandomly()
    }
    
    //MARK: Specific computer placement
    func selectEdgeIndex() {
        let edges = [1,3,5,7].shuffled()
        for edge in edges {
            guard !selectComputerMove(index: edge) else {
                //move completed
                return
            }
        }
        
        //all edges already selected
        selectComputerMoveRandomly()
    }
    
    func selectComputerMoveRandomly() {
        for i in (0...8).shuffled() {
            if selectComputerMove(index: i) {
                //spot available, selecting this for computer
                return
            }
        }
    }
    
    func selectComputerMove(index: Int, winningMove: Bool = false) -> Bool {
        guard !userSelections.contains(index) && !computerSelections.contains(index) else {
            //spot already taken
            return false
        }
        
        computerSelections.append(index)
        
        guard winningMove else {
            //just update single move
            let indexPath = IndexPath(row: index, section: 0)
            delegate?.refreshData(indexes: [indexPath])
            return true
        }
        
        delegate?.refreshData(indexes: nil)
        return true
    }
    
    //MARK: Next move leads to win or loss
    func winIfPossible() -> Bool {
        for combination in winningCombinations {
            var selectedIndexes: [Int] = []
            var combinationToComplete: [Int] = []
            
            for placement in combination {
                if computerSelections.contains(placement) {
                    selectedIndexes.append(placement)
                }
                combinationToComplete = combination
            }
            
            if selectedIndexes.count != 2 {
                //unable to complete combination
                continue
            }
            
            for index in combinationToComplete {
                guard !userSelections.contains(index) else {
                    //user already has placement here
                    break
                }
                
                if computerSelections.contains(index) {
                    //computer already has placement here
                } else {
                    log.info("place computer spot for the win at index: \(index)")
                    delegate?.setStatus(text: "Computer Won!")
                    StatTracker.saveGameHistory(statChange: .userLost)
                    //winning combination will be used to change icons for winning row
                    winningCombination = combinationToComplete
                    _ = selectComputerMove(index: index, winningMove: true)
                    return true
                }
            }
        }
        return false
    }
    
    func blockIfNeeded() -> Bool {
        for combination in winningCombinations {
            var selectedIndexes: [Int] = []
            var combinationToBlock: [Int] = []
            
            for placement in combination {
                if userSelections.contains(placement) {
                    selectedIndexes.append(placement)
                }
                combinationToBlock = combination
            }
            
            if selectedIndexes.count != 2 {
                //user can't complete this combination
                continue
            }
            
            for index in combinationToBlock {
                if userSelections.contains(index) || computerSelections.contains(index) {
                    //user already has placement here
                    //computer already has placement here
                } else {
                    log.info("blocked at index: \(index)")
                    _ = selectComputerMove(index: index)
                    return true
                }
            }
        }
        return false
    }
    
    //MARK: Game Status
    func checkIfGameIsOver() -> Bool {
        guard winningCombination.count == 0 else {
            //someone won the game
            return true
        }
        
        for i in (0...8) {
            guard userSelections.contains(i) || computerSelections.contains(i) else {
                //empty spot
                return false
            }
        }
        delegate?.setStatus(text: "Tied Game")
        StatTracker.saveGameHistory(statChange: .tiedGame)
        return true
    }
}

