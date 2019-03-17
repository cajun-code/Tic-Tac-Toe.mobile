//
//  GameViewController.swift
//  TicTacToe
//
//  Created by Naraharisetty, Venkat on 3/16/19.
//  Copyright © 2019 Naraharisetty, Venkat. All rights reserved.
//

import UIKit
import AudioToolbox

class GameViewController: UIViewController {
    
    var board:Board = Board()
    var AISearchEngine:AIEngine = AIEngine()
    var selectedButtonStyle:String?
    var isAIsTurn:Bool = false
    var AIChoice = Int()
    var usersImage:UIImage!
    var AIsImage:UIImage!
    var isGameAlreadyCompleted:Bool = false

    
     @IBOutlet weak var gameViewText: UILabel!

    
    //@IBOutlet weak var usersImageView: UIImageView!
    // ViewDidload-- Initiate static things here
    override func viewDidLoad() {
        super.viewDidLoad()
        if selectedButtonStyle == "O"{
            usersImage = UIImage(named: "oicon.png")
            AIsImage = UIImage(named: "xicon.png")
        } else {
            usersImage = UIImage(named: "xicon.png")
            AIsImage = UIImage(named: "oicon.png")
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
    }
    
    // Function called when any button on gameboard is manually ressed by the user
    @IBAction func gameButtonAction(_ sender: UIButton) {
        if !isGameAlreadyCompleted {
            if (board.getMove(atPosition:sender.tag-1) != 0 ) {
                showAlert(withString: "Cannot select same Box again")
            }
            else {
                let positionOfButton = sender.tag
                setImageOnButton(buttonTag:positionOfButton, playersRawValue:1)
                if !(self.checkForGameState(forUser:1, board:self.board)){
                    gameViewText.text = "Its Computers turn"
                    self.showSpinner(onView: self.view)
                    // just to show the loading indicator
                    DispatchQueue.main.asyncAfter(deadline: .now() + 0.2) {
                         self.AIsTurn()
                    }
                   
                }
            }
            
        } else {
            showAlert(withString:"Game is completed. Please restart the game")
        }
       
    }
    
    
    
    // Function called when user hits reset button manually
    @IBAction func resetGameButtonPressed(_ sender: Any) {
        resetGame()
    }
    
    
    // Function to reset the game -- This will be called internally whenevr game is over.
    func resetGame() {
        board.resetState()
        for i in  1...9 {
            if let button = view.viewWithTag(i) as?  UIButton {
                button.setImage(nil,for: [])
                button.backgroundColor = .gray
            }
        }
        isGameAlreadyCompleted = false
        gameViewText.text = "Please start the game"
    }
    
    
    // Artificial intelligence turn to make the move on board game
    func AIsTurn(){
        DispatchQueue.global(qos: .background).async {
            let moveOfAI = self.AISearchEngine.getAIsMove(board: self.board, player:2) + 1
                DispatchQueue.main.async {
                    self.setImageOnButton(buttonTag: moveOfAI, playersRawValue: 2)
                    
                    if (!self.checkForGameState(forUser:2, board:self.board)){
                        self.isAIsTurn = false
                        self.removeSpinner()
                        self.gameViewText.text = "Its your turn"
                    }
                    
            }
        }
       
    }
    
    
    
    // Setting Image on a Button based on user action or AI's move
    func setImageOnButton(buttonTag:Int, playersRawValue:Int) {
        let tempButton = self.view.viewWithTag(buttonTag) as! UIButton
            if playersRawValue == 1 {
                tempButton.setImage(usersImage, for: [])
            } else {
                tempButton.setImage(AIsImage, for: [])
            }
        board.addMove(playerID: playersRawValue, atPosition: buttonTag-1)
    }
    
    
    func checkForGameState(forUser currentUser:Int, board:Board) -> Bool {
        let result = board.checkIfGameIsWonUpdated(byUser:currentUser, gameState: board)
        if (result.0){
            let alertString:String = (currentUser == 2) ? "AI won the game": "You won the game"
            showAlert(withString: alertString)
            self.gameViewText.text = alertString
            self.removeSpinner()
            showWhoWonTheGame(currentUser:currentUser, combination:result.1)
            isGameAlreadyCompleted = true
            return true
        }
        if board.checkIfMovesAreComplete(gameState: board){
            showAlert(withString: "Game complete. Its a draw")
            isGameAlreadyCompleted = true
            self.removeSpinner()
            return true
        }
        return false
    }
    
    func showWhoWonTheGame(currentUser:Int, combination:[Int]){
        for index in combination {
            let tempButton = self.view.viewWithTag(index+1) as! UIButton
            tempButton.backgroundColor = UIColor.black
        }
    }
    
    
    // Alert function which displays alert taking input string as parameter
    func showAlert(withString message:String){
        playSound()
        let alert = UIAlertController(title: "Game Status", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "ok", style: .cancel, handler: { action in
        }))
        self.present(alert, animated: true)
    }
    
    // Function to play sound whenever game is complete
    func playSound() {
        AudioServicesPlaySystemSound(SystemSoundID(1007))
    }
    
    
    
    
}
