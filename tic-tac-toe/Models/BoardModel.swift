//
//  BoardModel.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import Foundation

enum CellOwner: String {
    case none = " "
    case player = "x"
    case opponent = "o"
    
    func textual() -> String {
        return self.rawValue
    }
}

class BoardModel {
    var board: [[String]] = []
    
    init() {
        resetBoard()
//        board = [
//            ["o", " ", "x"],
//            [" ", "x", " "],
//            ["o", " ", " "]
//        ]
    }
    
    func updateBoard(atRow row: Int, col: Int, setBy newOwner: CellOwner) {
        board[row][col] = newOwner.rawValue
    }
    
    func resetBoard() {
        board = [
            [" ", " ", " "],
            [" ", " ", " "],
            [" ", " ", " "]
        ]
    }
}
