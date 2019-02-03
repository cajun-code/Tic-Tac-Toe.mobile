//
//  ViewController.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.addSubview(boardView)
        
        boardView.onTurn = { boardModel in
            print(boardModel.board)
            let bestMove = Opponent.shared.findBestMove(&boardModel.board)
            print(bestMove)
            self.boardView.makeTurn(onRow: bestMove.row, col: bestMove.col, setBy: .opponent)
            print(boardModel.board)
            print("================")
        }
    }
    
    lazy var boardView: Board = {
        let board = Board(frame: view.frame)
        board.boardModel = BoardModel()
        return board
    }()
}
