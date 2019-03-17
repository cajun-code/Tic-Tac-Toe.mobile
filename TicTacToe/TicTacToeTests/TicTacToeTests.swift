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
    

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
