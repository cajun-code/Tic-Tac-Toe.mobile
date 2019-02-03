//
//  tic_tac_toeTests.swift
//  tic-tac-toeTests
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import XCTest
@testable import tic_tac_toe

class tic_tac_toeTests: XCTestCase {

    func testComputerTakesWinShot() {
        var board = [
            ["o", " ", "x"],
            [" ", "x", " "],
            ["o", " ", " "]
        ]
        let winMove = Opponent.shared.findBestMove(&board)
        XCTAssert(winMove.row == 1)
        XCTAssert(winMove.col == 0)
    }
    
    func testComputerBlocksUsersWinShot() {
        var board = [
            ["o", " ", "x"],
            [" ", "o", " "],
            [" ", " ", "x"]
        ]
        let blockMove = Opponent.shared.findBestMove(&board)
        XCTAssert(blockMove.row == 1)
        XCTAssert(blockMove.col == 2)
    }
    
    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
}
