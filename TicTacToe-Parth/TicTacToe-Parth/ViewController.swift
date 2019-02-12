//
//  ViewController.swift
//  TicTacToe-Parth
//
//  Created by Rucha on 2/9/19.
//  Copyright © 2019 Parth. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    // MARK: - Properties
    // MARK: - gameBoard -> (-1) for Empty Space, 1 for User Space, 0 for AI Space
    var gameBoard = [-1, -1, -1, -1, -1, -1, -1, -1, -1]
    
    //    0  |   1  |  2
    //   ----|------|-----
    //    3  |   4  |  5
    //   ----|------|-----
    //    6  |   7  |  8
    let winPatterns = [[0, 1, 2], [3, 4, 5], [6, 7, 8], [0, 3, 6], [1, 4, 7], [2, 5, 8], [0, 4, 8], [2, 4, 6]]
    var gameIsActive = true
    
    // MARK: - IBOutlets
    @IBOutlet weak var statusLabel: UILabel!
    @IBOutlet weak var playAgainButton: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        statusLabel.text = "You go first"
    }

    @IBAction func UserAction(_ sender: UIButton) {
        if gameBoard[sender.tag - 1] == -1 && gameIsActive == true {
            sender.setImage(UIImage(named: "X.png"), for: .normal)
            gameBoard[sender.tag - 1] = 1
            checkForWin()
            AIMove()
        }
    }
    
    @IBAction func playAgainButtonPressed(_ sender: Any) {
        gameBoard = [-1, -1, -1, -1, -1, -1, -1, -1, -1]
        gameIsActive = true
        
        statusLabel.text = "You go first"
        playAgainButton.isHidden = true
        
        for tag in 1...9 {
            let button = view.viewWithTag(tag) as! UIButton
            button.setImage(nil, for: .normal)
        }
    }
    
    // MARK: - Check all winning Combinationes and draw condition
    func checkForWin() {
        for pattern in winPatterns {
            if gameBoard[pattern[0]] != -1 &&
                gameBoard[pattern[0]] == gameBoard[pattern[1]] &&
                gameBoard[pattern[1]] == gameBoard[pattern[2]] {
                if gameBoard[pattern[0]] == 1 {
                    statusLabel.text = "User 'X' Win"
                } else {
                    statusLabel.text = "Computer 'O' Win"
                }
                gameIsActive = false
                playAgainButton.isHidden = false
            } else {
                gameIsActive = false
                for i in gameBoard {
                    if i == -1 {
                        gameIsActive = true
                        break
                    }
                }
                if gameIsActive == false {
                    statusLabel.text = "It was a draw"
                    playAgainButton.isHidden = false
                    break
                }
            }
        }
    }
    
    // MARK: - AI check from worst to best possible move
    func AIMove() {
        var possibleAIMove: Int!
        
        //MARK: - Check for Sides move
        if let sidePlay = checkForSides() {
            possibleAIMove = sidePlay
        }
        
        //MARK: - Check for Corners move
        if let cornerPlay = checkForCorners() {
            possibleAIMove = cornerPlay
        }
        
        //MARK: - Check for Center move
        if gameBoard[4] == -1 {
            possibleAIMove = 4
        }
        
        // MARK: - Block User from first forking Strategy
        if let fork1 = blockForkStrategy1() {
            possibleAIMove = fork1
        }
        
        // MARK: - Block User from Second forking Strategy
        if let fork2 = blockForkStrategy2() {
            possibleAIMove = fork2
        }
        
        // MARK: - Block User from Third forking Strategy
        if let fork3 = blockForkStrategy3() {
            possibleAIMove = fork3
        }
        
        // MARK: - Block User win Move
        if let blockPossible = blockUserWinMove() {
            possibleAIMove = blockPossible
        }
        
        // MARK: - Check AI win possibility
        if let winPossible = checkAIWinMove() {
            possibleAIMove = winPossible
        }
        
        // MARK: Find AI possible move, set image and check for win
        if let _ = possibleAIMove {
            gameBoard[possibleAIMove] = 0
            let button = view.viewWithTag(possibleAIMove + 1) as! UIButton
            button.setImage(UIImage(named: "O.png"), for: .normal)
            statusLabel.text = "Continue..."
            checkForWin()
        }
    }
}

// MARK: - FOR ALL HELPER METHODS OF AI Move
extension ViewController {
    
    func checkForSides() -> Int? {
        if gameBoard[1] == -1 {
            return 1
        }
        if gameBoard[3] == -1 {
            return 3
        }
        if gameBoard[5] == -1 {
            return 5
        }
        if gameBoard[7] == -1 {
            return 7
        }
        return nil
    }
    
    func checkForCorners() -> Int? {
        if gameBoard[0] == -1 {
            return 0
        }
        if gameBoard[2] == -1 {
            return 2
        }
        if gameBoard[6] == -1 {
            return 6
        }
        if gameBoard[8] == -1 {
            return 8
        }
        return nil
    }
    
    //  First forking Strategy
    //         |   X  |                                    
    //     ----|------|-----
    //       X |   O  |          O - AI (Computer)
    //     ----|------|-----     X - User
    //         |      |  $       $ - Bad Move for AI
    func blockForkStrategy1() -> Int? {
        if gameBoard[1] == 1 && gameBoard[3] == 1 && gameBoard[0] == -1 {
            return 0
        }
        if gameBoard[3] == 1 && gameBoard[7] == 1 && gameBoard[6] == -1 {
            return 6
        }
        if gameBoard[7] == 1 && gameBoard[5] == 1 && gameBoard[8] == -1 {
            return 8
        }
        if gameBoard[5] == 1 && gameBoard[1] == 1 && gameBoard[2] == -1 {
            return 2
        }
        return nil
    }
    
    // Second forking Strategy
    //       X |      |  $
    //     ----|------|-----
    //         |   O  |          O - AI (Computer)
    //     ----|------|-----     X - User
    //      $  |      | X        $ - Bad Move for AI
    func blockForkStrategy2() -> Int? {
        if (gameBoard[0] == 1 && gameBoard[8] == 1 && gameBoard[6] == -1 && gameBoard[2] == -1) ||
            (gameBoard[2] == 1 && gameBoard[6] == 1 && gameBoard[0] == -1 && gameBoard[8] == -1) {
            if let sidePlay = checkForSides() {
                return sidePlay
            }
        }
        return nil
    }
    
    // Third forking Strategy
    //       X |      |  $
    //     ----|------|-----
    //         |   O  |          O - AI (Computer)
    //     ----|------|-----     X - User
    //         |   X  |          $ - Bad Move for AI
    func blockForkStrategy3() -> Int? {
        if gameBoard[0] == 1 && gameBoard[7] == 1 {
            if gameBoard[6] == -1 {
                return 6
            } else if gameBoard[3] == -1 {
                return 3
            }
        }
        if gameBoard[0] == 1 && gameBoard[5] == 1 {
            if gameBoard[2] == -1 {
                return 2
            } else if gameBoard[1] == -1 {
                return 1
            }
        }
        if gameBoard[2] == 1 && gameBoard[7] == 1 {
            if gameBoard[8] == -1 {
                return 8
            } else if gameBoard[5] == -1 {
                return 5
            }
        }
        if gameBoard[2] == 1 && gameBoard[3] == 1 {
            if gameBoard[0] == -1 {
                return 0
            } else if gameBoard[1] == -1 {
                return 1
            }
        }
        if gameBoard[6] == 1 && gameBoard[1] == 1 {
            if gameBoard[0] == -1 {
                return 0
            } else if gameBoard[3] == -1 {
                return 3
            }
        }
        if gameBoard[6] == 1 && gameBoard[5] == 1 {
            if gameBoard[8] == -1 {
                return 8
            } else if gameBoard[7] == -1 {
                return 7
            }
        }
        if gameBoard[8] == 1 && gameBoard[1] == 1 {
            if gameBoard[2] == -1 {
                return 2
            } else if gameBoard[5] == -1 {
                return 5
            }
        }
        if gameBoard[8] == 1 && gameBoard[3] == 1 {
            if gameBoard[6] == -1 {
                return 6
            } else if gameBoard[7] == -1 {
                return 7
            }
        }
        return nil
    }
    
    func blockUserWinMove() -> Int? {
        for pattern in winPatterns {
            if (gameBoard[pattern[0]] == 1 && gameBoard[pattern[1]] == 1 && gameBoard[pattern[2]] == -1) {
                return pattern[2]
            }
            if (gameBoard[pattern[0]] == 1 && gameBoard[pattern[2]] == 1 && gameBoard[pattern[1]] == -1) {
                return pattern[1]
            }
            if (gameBoard[pattern[1]] == 1 && gameBoard[pattern[2]] == 1 && gameBoard[pattern[0]] == -1) {
                return pattern[0]
            }
        }
        return nil
    }
    
    func checkAIWinMove() -> Int? {
        for pattern in winPatterns {
            if (gameBoard[pattern[0]] == 0 && gameBoard[pattern[1]] == 0 && gameBoard[pattern[2]] == -1) {
                return pattern[2]
            }
            if (gameBoard[pattern[0]] == 0 && gameBoard[pattern[2]] == 0 && gameBoard[pattern[1]] == -1) {
                return pattern[1]
            }
            if (gameBoard[pattern[1]] == 0 && gameBoard[pattern[2]] == 0 && gameBoard[pattern[0]] == -1) {
                return pattern[0]
            }
        }
        return nil
    }
}

