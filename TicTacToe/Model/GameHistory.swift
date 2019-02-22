//
//  GameHistory.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import RealmSwift

@objc class GameHistory: Object {
    @objc public dynamic var wins = 0
    @objc public dynamic var ties = 0
    @objc public dynamic var losses = 0
    
    @objc convenience init(wins: Int, ties: Int, losses: Int) {
        self.init()
        self.wins = wins
        self.ties = ties
        self.losses = losses
    }
}
