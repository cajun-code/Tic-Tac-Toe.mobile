//
//  TicTacToe_ParthTests.swift
//  TicTacToe-ParthTests
//
//  Created by Rucha on 2/9/19.
//  Copyright © 2019 Parth. All rights reserved.
//

import XCTest
@testable import TicTacToe_Parth

class TicTacToe_ParthTests: XCTestCase {

    var gameUnderTest: GameViewController!
    
    override func setUp() {
        gameUnderTest = GameViewController()
    }

    override func tearDown() {
        gameUnderTest = nil
    }
    
    func testBlockForkStrategy1() {
        gameUnderTest.gameBoard = [-1, -1, -1, -1, 0, 1, -1, 1, -1]
        XCTAssertEqual(gameUnderTest.blockForkStrategy1(), 8, "Test Fail in blockForkStrategy1")
    }
    
    func testBlockForkStrategy2() {
        gameUnderTest.gameBoard = [-1, -1, 1, -1, 0, -1, 1, -1, -1]
        XCTAssertEqual(gameUnderTest.blockForkStrategy2(), 1, "Test Fail in blockForkStrategy2")
    }
    
    func testBlockForkStrategy3() {
        gameUnderTest.gameBoard = [-1, -1, -1, 1, 0, -1, -1, -1, 1]
        XCTAssertEqual(gameUnderTest.blockForkStrategy3(), 6, "Test Fail in blockForkStrategy3")
    }
    
    func testTwoInRow() {
         gameUnderTest.gameBoard = [1, -1, 0, 1, 0, -1, -1, -1, -1]
        let computer = 0
        XCTAssertEqual(gameUnderTest.twoInRow(forPlayer: computer), 6, "Test Fail in twoInRow")
    }
    
    func testCheckForNilValue() {
        gameUnderTest.gameBoard = [0, 1, 0, 1, 0, 1, 1, 0, 1]
        XCTAssertNil(gameUnderTest.checkForSides(), "Test Fail in checkForSides")
        XCTAssertNil(gameUnderTest.checkForCorners(), "Test Fail in checkForCorners")
    }

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
