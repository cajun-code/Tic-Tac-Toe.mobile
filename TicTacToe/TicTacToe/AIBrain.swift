//
//  AIBrain.swift
//  TicTacToe
//
//  Created by Arihant Arora on 3/29/19.
//  Copyright © 2019 Arihant Arora. All rights reserved.
//

import UIKit

class AIBrain: NSObject {
    
    var Game = GameViewController()
    
    
    func Win(Arr : Array<Int>) -> Bool {
        var count = Int()
        var win = false
        for i in 0 ..< Game.possibleWinSituations.count
        {
            count = 0
            for j in 0 ..< Game.possibleWinSituations[i].count
            {
                if Arr.contains((Game.possibleWinSituations[i])[j])
                {
                    count += 1
                }
                
                if count == 3 {
                    win = true
                    break
                }
            }
            if count == 3 {
                break
            }
        }
        return win
    }
}
