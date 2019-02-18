//
//  ViewController.swift
//  TicTacToe
//
//  Created by Mingu Chu on 2/16/19.
//  Copyright © 2019 Mike Chu. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    

    
//    Default
    var playerView = "O"
    var computerView = "X"
    
    var gameActive = true
    var gameBoard = GameBoard()
    var minMax = TicTacToeAI()
    
    private func gameButton(button tag: Int) -> UIButton {
        let button = UIButton(frame: CGRect(x: 0, y: 0, width: 90, height: 90))
        button.backgroundColor = .blue
        button.titleLabel?.font = .boldSystemFont(ofSize: 16)
        button.tag = tag
        button.layer.cornerRadius = 10
        button.translatesAutoresizingMaskIntoConstraints = false
        button.addTarget(self, action: #selector(buttonPressed), for: .touchUpInside)
        return button
    }
    
    private func gameStack(axis: NSLayoutConstraint.Axis) -> UIStackView {
        let stackView = UIStackView()
        stackView.axis = axis
        stackView.distribution = .fillEqually
        stackView.alignment = .fill
        stackView.spacing = 5
        stackView.translatesAutoresizingMaskIntoConstraints = false
        return stackView
    }
    
    let gameResultLabel: UILabel = {
        let label = UILabel()
        label.font = .systemFont(ofSize: 50)
        label.textColor = .white
        label.textAlignment = .center
        label.layer.cornerRadius = 25
        label.layer.borderWidth = 10
        label.layer.borderColor = UIColor.white.cgColor
        label.translatesAutoresizingMaskIntoConstraints = false
        return label
    }()
    
    let replayButton: UIButton = {
        let button = UIButton()
        button.layer.borderWidth = 5
        button.layer.borderColor = UIColor.white.cgColor
        button.layer.cornerRadius = 10
        button.titleLabel?.textColor = .black
        button.titleLabel?.numberOfLines = 0
        button.titleLabel?.textAlignment = .center
        button.setTitle("Play \n Again", for: .normal)
        button.titleLabel?.font = .systemFont(ofSize: 20)
        button.translatesAutoresizingMaskIntoConstraints = false
        button.addTarget(self, action: #selector(restartGame), for: .touchUpInside)
        return button
    }()
    
    
    private func displayGameBoard() {
        let boardStack = gameStack(axis: .vertical)
        let topRowStack = gameStack(axis: .horizontal)
        let midRowStack = gameStack(axis: .horizontal)
        let botRowStack = gameStack(axis: .horizontal)
    
        for tag in 1...9 {
            let button = gameButton(button: tag)
            switch tag {
            case 1...3:
                topRowStack.addArrangedSubview(button)
            case 4...6:
                midRowStack.addArrangedSubview(button)
            case 7...9:
                botRowStack.addArrangedSubview(button)
            default:
                break
            }
        }
        
        boardStack.addArrangedSubview(topRowStack)
        boardStack.addArrangedSubview(midRowStack)
        boardStack.addArrangedSubview(botRowStack)
        self.view.addSubview(boardStack)
        
        boardStack.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        boardStack.centerYAnchor.constraint(equalTo: self.view.centerYAnchor).isActive = true
        boardStack.widthAnchor.constraint(equalTo: self.view.widthAnchor, constant: -50).isActive = true
        boardStack.heightAnchor.constraint(equalTo: self.view.widthAnchor, constant: -50).isActive = true
    }
    
    private func displayGameResult() {
        self.view.addSubview(gameResultLabel)
        self.view.addSubview(replayButton)
        
        gameResultLabel.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        gameResultLabel.centerYAnchor.constraint(equalTo: self.view.centerYAnchor, constant: -100).isActive = true
        gameResultLabel.widthAnchor.constraint(equalTo: self.view.widthAnchor, constant: -50).isActive = true
        gameResultLabel.heightAnchor.constraint(equalToConstant: 100).isActive = true
        
        replayButton.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        replayButton.centerYAnchor.constraint(equalTo: self.view.centerYAnchor, constant: 100).isActive = true
        replayButton.widthAnchor.constraint(equalToConstant: 90).isActive = true
        replayButton.heightAnchor.constraint(equalToConstant: 90).isActive = true
    }
    
    private func displayGameResult(message:String){
        gameResultLabel.text = message
        self.gameResultLabel.isHidden = false
        self.replayButton.isHidden = false
    }
    
    private func displaySelectionSheet() {
        
        let selectionMenu = UIAlertController(title: "Choose Option", message: nil, preferredStyle: .alert)
        
        let crossAction = UIAlertAction(title: "X", style: .default) { _ in
            self.playerView = "X"
            self.computerView = "O"
        }
        
        let circleAction = UIAlertAction(title: "O", style: .default) { _ in
            self.playerView = "O"
            self.computerView = "X"
        }
        
        selectionMenu.addAction(circleAction)
        selectionMenu.addAction(crossAction)
        
        self.present(selectionMenu, animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .black

        displayGameBoard()
        displayGameResult()
        resetUI()
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        displaySelectionSheet()
    }

}

// MARK : GameLogic
extension ViewController {
    
    @objc private func buttonPressed(sender: UIButton) {
        
        if gameBoard.getMove(onPosition: sender.tag - 1) == 0 && gameActive {
            sender.setTitle(playerView, for: .normal)
            sender.setTitleColor(.black, for: .normal)
            self.gameBoard.addMove(player: Player.human.rawValue, onPosition: sender.tag - 1)
            gameActive = false
            self.playComputerMove()
        }
    }
    
    private func playComputerMove(){
        let nextMove = self.minMax.nextMove(board: gameBoard, player: Player.computer.rawValue)
        let status = self.gameBoard.checkGameStatus()

        if nextMove >= 0 {
            let button = self.view.viewWithTag(nextMove + 1) as! UIButton
            button.setTitle(computerView, for: .normal)
            button.setTitleColor(.black, for: .normal)
            self.gameBoard.addMove(player: Player.computer.rawValue, onPosition: nextMove)
        }
        
        if status != GameStatus.inProgress {
            switch status {
            case GameStatus.lose:
                self.displayGameResult(message: "Computer Win!")
            case GameStatus.win:
                self.displayGameResult(message: "You win!")
            case GameStatus.tie:
                self.displayGameResult(message: "Draw!")
            default:
                break
            }
            gameActive = false
        } else {
            gameActive = true
        }
    }
    
    @objc private func restartGame() {
        gameActive = true
        gameBoard.resetBoardState()
        resetUI()
        displaySelectionSheet()
    }
    
    private func resetUI() {
        gameResultLabel.isHidden = true
        replayButton.isHidden = true
        for i in 1...9 {
            guard let button = view.viewWithTag(i) as? UIButton else { return }
            button.setTitle("", for: .normal)
        }
    }

}

