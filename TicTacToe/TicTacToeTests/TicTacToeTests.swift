//
//  TicTacToeTests.swift
//  TicTacToeTests
//
//  Created by Arihant Arora on 3/28/19.
//  Copyright © 2019 Arihant Arora. All rights reserved.
//

import XCTest
@testable import TicTacToe

class TicTacToeTests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    
    func testIsWin() {
        let model = AIBrain()
        //model.initialize()
        var Win = model.Win(Arr: [0,4,8])
        assert(Win == true, "Win succeeded")
        
        Win = model.Win(Arr: [1,5,2])
        assert(Win == false, "Win failed")
    }


    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
