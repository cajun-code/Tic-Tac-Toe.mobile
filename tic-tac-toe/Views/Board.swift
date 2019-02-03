//
//  Board.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import UIKit

class Board: UIView {
    
    var onTurn: (() -> ())?
    
    var boardModel: BoardModel? {
        didSet {
            if let model = boardModel {
                createBoard(withBoardModel: model)
            }
        }
    }
    
    func createBoard(withBoardModel boardModel: BoardModel) {
        let cellWidth = Int(bounds.width / 3)
        let cellHeight = Int(bounds.height / 3)
        for i in 0...2 {
            for j in 0...2 {
                let cell = Cell(frame:
                    CGRect(x: i * cellWidth, y: j * cellHeight, width: cellWidth, height: cellHeight)
                )
                cell.actionBlock = { [weak self] in
                    self?.boardModel?.updateBoard(atRow: i, col: j, setBy: .player)
                    self?.onTurn?()
                }
                self.addSubview(cell)
            }
        }
    }
}
