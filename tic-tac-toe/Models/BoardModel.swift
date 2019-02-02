//
//  BoardModel.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import Foundation

enum CellOwner: Int {
    case none
    case player
    case opponent
    
    func textual() -> String {
        switch self {
        case .player:
            return " "
        case .opponent:
            return " "
        case .none:
            return " "
        }
    }
}

struct BoardModel {
    var board: [[String]]
    
    init() {
        board = [
            ["o", " ", " "],
            [" ", "x", " "],
            [" ", " ", "o"]
        ]
    }
}
