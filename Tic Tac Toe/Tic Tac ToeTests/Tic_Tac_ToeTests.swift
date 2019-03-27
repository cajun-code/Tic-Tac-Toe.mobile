//
//  Tic_Tac_ToeTests.swift
//  Tic Tac ToeTests
//
//  Created by Ome Prakash on 03/22/19.
//  Copyright © 2019 Ome Prakash. All rights reserved.
//

import XCTest
@testable import Tic_Tac_Toe

class Tic_Tac_ToeTests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testIsWin() {
        let vc = ViewController()
        vc.initialize()
        var isWin = vc.isWin(Arr: [0,1,2])
        assert(isWin, "is Win failed")
        
        isWin = vc.isWin(Arr: [1,5,2])
        assert(isWin == false, "is Win failed")
    }
    
    func testReset() {
        let vc = ViewController()
        vc.initialize()
        vc.reset()
        
        assert(vc.crossMoves.count == 0, "reset failed")
        assert(vc.zeroMoves.count == 0, "reset failed")
    }
    
    
}
