//
//  Board.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import UIKit

class Board: UIView {
    
    lazy var cells: [[Cell]] = {
        var arr: [[Cell]] = []
        for i in 0...2 {
            arr.append([])
        }
        return arr
    }()
    
    var onTurn: ((BoardModel) -> ())?
    
    var boardModel: BoardModel? {
        didSet {
            if let model = boardModel {
                createBoard(withBoardModel: model)
            }
        }
    }
    
    func makeTurn(onRow row: Int, col: Int, setBy cellOwner: CellOwner) {
        boardModel?.updateBoard(atRow: row, col: col, setBy: cellOwner)
        updateModel()
    }
    
    func update() {
        self.updateModel()
    }
    
    private func updateModel() {
        for i in 0...2 {
            for j in 0...2 {
                let cell = cells[i][j]
                cell.setBy = CellOwner(rawValue: boardModel?.board[i][j] ?? " ") ?? .none
            }
        }
    }
    
    private func createBoard(withBoardModel boardModel: BoardModel) {
        let cellWidth = Int(bounds.width / 3)
        let cellHeight = Int(bounds.height / 3)
        for i in 0...2 {
            for j in 0...2 {
                let cell = Cell(frame:
                    CGRect(x: j * cellWidth, y: i * cellHeight, width: cellWidth, height: cellHeight)
                )
                cell.actionBlock = { [weak self] in
                    self?.boardModel?.updateBoard(atRow: i, col: j, setBy: .player)
                    self?.onTurn?(boardModel)
                }
                cells[i].append(cell)
                self.addSubview(cell)
            }
        }
        updateModel()
    }
}
