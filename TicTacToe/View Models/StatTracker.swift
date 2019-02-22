//
//  StatTracker.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import RealmSwift

enum StatChange {
    case userWon
    case userLost
    case tiedGame
}

struct StatTracker {
    static func saveGameHistory(statChange: StatChange) {
        let realm = try! Realm()
        try! realm.write {
            guard let oldStats = realm.objects(GameHistory.self).toArray(ofType: GameHistory.self).first else {
                //create new object
                let stats = GameHistory()
                
                switch statChange {
                case .userWon:
                    stats.wins = 1
                case .userLost:
                    stats.losses = 1
                case .tiedGame:
                    stats.ties = 1
                }
                
                realm.add(stats)
                return
            }
            
            switch statChange {
            case .userWon:
                oldStats.wins += 1
            case .userLost:
                oldStats.losses += 1
            case .tiedGame:
                oldStats.ties += 1
            }
        }
    }
    
    static func getStats() -> GameHistory? {
        let realm = try! Realm()
        return realm.objects(GameHistory.self).toArray(ofType: GameHistory.self).first 
    }
}

//realm extention to get array of objects
extension Results {
    func toArray<T>(ofType: T.Type) -> [T] {
        var array = [T]()
        for i in 0 ..< count {
            if let result = self[i] as? T {
                array.append(result)
            }
        }
        return array
    }
}
