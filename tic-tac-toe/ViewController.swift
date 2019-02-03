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
        
        boardView.onTurn = {
            
        }
//        var board = [
//            ["o", " ", " "],
//            [" ", "x", " "],
//            [" ", " ", "o"]
//        ]
        
//        let bestMove = findBestMove(&board)
    }
    
    lazy var boardView: Board = {
        let board = Board(frame: view.frame)
        board.boardModel = BoardModel()
        return board
    }()
}
