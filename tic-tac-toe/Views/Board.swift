//
//  Board.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import UIKit

class Board: UIView {
    var boardModel: BoardModel? {
        didSet {
            if let model = boardModel {
                updateBoard(withBoardModel: model)
            }
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        let cellWidth = Int(bounds.width / 3)
        let cellHeight = Int(bounds.height / 3)
        for i in 0...2 {
            for j in 0...2 {
                let cell = Cell(frame:
                    CGRect(x: i * cellWidth, y: j * cellHeight, width: cellWidth, height: cellHeight)
                )
                self.addSubview(cell)
            }
        }
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func updateBoard(withBoardModel boardModel: BoardModel) {
        
    }
}
