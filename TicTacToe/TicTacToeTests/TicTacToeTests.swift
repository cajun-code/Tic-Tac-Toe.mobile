//
//  TicTacToeTests.swift
//  TicTacToeTests
//
//  Created by Naraharisetty, Venkat on 3/16/19.
//  Copyright © 2019 Naraharisetty, Venkat. All rights reserved.
//

import XCTest
@testable import TicTacToe

class TicTacToeTests: XCTestCase {
    
    var board = Board()
    
    //This method is called before the invocation of each test method in the class.
    override func setUp() {
        super.setUp()
        self.board = Board()
    }
    
    // Put teardown code here. This method is called after the invocation of each test method in the class.
    override func tearDown() {
        
    }
    
    func testResetBoardFunction() {
        let emptyBoard = Board()
        let addedEmptyBoard = Board()
        addedEmptyBoard.addMove(playerID: 2, atPosition: 2)
        addedEmptyBoard.addMove(playerID: 1, atPosition: 4)
        XCTAssertNotEqual(emptyBoard.boardState, addedEmptyBoard.boardState, "Reset Board not working")
        addedEmptyBoard.resetState()
        XCTAssertEqual(emptyBoard.boardState, addedEmptyBoard.boardState, "Reset Board function not working")
        
    }
    
    func testAddMoveFunction() {
        let board = Board()
        board.addMove(playerID: 2, atPosition: 2)
        XCTAssert(board.getMove(atPosition: 2) == 2 , "testAddMoveFunction: Test Failed ")
        XCTAssertFalse(board.getMove(atPosition: 2) == 1 , "testAddMoveFunction: Test Failed 222 ")
    }
    
    func testRemoveMoveFunction(){
        let board = Board()
        board.boardState = [0,1,2,1,2,0,0,0,0]
        board.removeMove(fromPosition: 2)
        XCTAssert(board.getMove(atPosition: 2) == 0 , "testRemoveMoveFunction: Test Failed ")
    }
    
    func testCheckIfUserWon(){
        let board = Board()
        board.boardState = [0,1,2,1,2,2,1,1,1]
        XCTAssertTrue(board.checkIfGameIsWonUpdated(byUser: 1, gameState: board).0, "testCheckIfUserWon: Test Passed")
         XCTAssertFalse(board.checkIfGameIsWonUpdated(byUser: 2, gameState: board).0, "testCheckIfUserWon: Test Passed")
        
        }
    

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
