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
            // =====================
            
            let checkWhoWon = { return Opponent.shared.whoWon(board: boardModel.board) }
            let makeResultsMessage: (String?) -> (String) = { winner in
                var message = ""
                if winner == nil {
                    message = "draw"
                } else {
                    message = winner == CellOwner.player.rawValue ? "You won" : "Computer won. Better luck next time"
                }
                
                return message
            }
            let showResults: (String) -> () = { message in
                let alert = UIAlertController(title: nil, message: message, preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "OK", style: .cancel, handler: { (action) in
                    boardModel.resetBoard()
                    self.boardView.update()
                }))
                self.present(alert, animated: true, completion: nil)
            }
            let someonesWon: () -> (Bool) = {
                return checkWhoWon() != nil
            }
            let showConclusions = {
                let message = makeResultsMessage(checkWhoWon())
                showResults(message)
            }
            
            // =====================
            
            if Opponent.shared.anyMovesLeft(boardModel.board) {
                let bestMove = Opponent.shared.findBestMove(&boardModel.board)
                self.boardView.makeTurn(onRow: bestMove.row, col: bestMove.col, setBy: .opponent)
            } else {
                showConclusions()
            }
            
            if someonesWon() {
                showConclusions()
            }
        }
    }
    
    lazy var boardView: Board = {
        let board = Board(frame: view.frame)
        board.boardModel = BoardModel()
        return board
    }()
}
