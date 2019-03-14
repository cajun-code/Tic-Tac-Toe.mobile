//
//  ViewController.swift
//  Tic-Toc-Toe
//
//  Created by Sudhir Kumar on 3/13/19.
//  Copyright © 2019 Sudhir Kumar. All rights reserved.
//

import UIKit


extension UIView{
    
    func setBorder(radius:CGFloat, color:UIColor = UIColor.clear) {
        self.layer.cornerRadius = CGFloat(radius)
        self.layer.borderWidth = 4
        self.layer.borderColor = color.cgColor
        self.clipsToBounds = true
        
    }
}

extension UIViewController {
    
    func presentAlertWithTitle(title: String, message: String, options: String..., completion: @escaping (Int) -> Void) {
        let alertController = UIAlertController(title: title, message: message, preferredStyle: .alert)
        for (index, option) in options.enumerated() {
            alertController.addAction(UIAlertAction.init(title: option, style: .default, handler: { (action) in
                completion(index)
            }))
        }
        self.present(alertController, animated: true, completion: nil)
    }
}

class ViewController: UIViewController {
    
    enum PlayerRolev : String{
        case Player1
        case Player2
    }
    @IBOutlet weak var playerOneView: UIView!
    @IBOutlet weak var playerTwoView: UIView!
    
    var currentPlayer = PlayerRolev.Player1
    
    @IBOutlet weak var playerTwoWinCountLbl: UILabel!
    @IBOutlet weak var playerOneWinCountLbl: UILabel!
    var ticTocValueArray = Array(repeating: Array(repeating: -1, count: 3), count: 3)
    var isWinnerFound = false
    var refButtonArray = [UIButton]()
    

    override func viewDidLoad() {
        super.viewDidLoad()
        self.setUpNewGame()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    @IBAction func actionOnTile(_ sender: UIButton) {
        
        print(ticTocValueArray)
        
        let senderTagArray = (String(sender.tag))
        
        guard let firstChar = senderTagArray.first, let rowindex = Int(String(firstChar)) else {
            return
        }
        
        guard let secondChar = senderTagArray.last, let columnIndex = Int(String(secondChar)) else {
            return
        }
        
        guard ticTocValueArray[rowindex - 1][columnIndex] == -1 else {
            return
        }
        print("")
        
        switch currentPlayer {
        case .Player1:
            ticTocValueArray[rowindex - 1][columnIndex] = 1
            sender.setTitle("X", for: UIControl.State.normal)
            self.currentPlayer = .Player2
            playerTwoView.setBorder(radius: 5.0, color: UIColor.blue)
            playerOneView.setBorder(radius: 5.0)
            break
            
        case .Player2:
            ticTocValueArray[rowindex - 1][columnIndex] = 0
            sender.setTitle("O", for: UIControl.State.normal)
            self.currentPlayer = .Player1
            playerOneView.setBorder(radius: 5.0, color: UIColor.blue)
            playerTwoView.setBorder(radius: 5.0)
            break
        }
        
        refButtonArray.append(sender)
        
        print("\(currentPlayer.rawValue)")
        
        defer {
            checkForWinnerInCoulmn()
            checkForWinnerInDiagonal()
            checkForWinnerInRows()
        }
        
    }
    
    
    func checkForWinnerInRows() -> () {
        guard !isWinnerFound else {
            return
        }
        self.lookForWinner(inputRowOrColumnArray: ticTocValueArray)
    }
    
    
    func checkForWinnerInDiagonal() -> () {
        guard !isWinnerFound else {
            return
        }
        let firstDaigonalValueArray = [ticTocValueArray[0][0],ticTocValueArray[1][1], ticTocValueArray[2][2]]
        let secondDiagonalArray = [ticTocValueArray[0][2],ticTocValueArray[1][1], ticTocValueArray[2][0]]
        self.lookForWinner(inputRowOrColumnArray: [firstDaigonalValueArray, secondDiagonalArray])
        
    }
    
    func checkForWinnerInCoulmn() -> () {
        guard !isWinnerFound else {
            return
        }
        var allCoulumArray = [[Int]]()
        for colIndex in 0...2{
            var columArray = [Int]()
            for rowIndex in 0...2{
                print("\(colIndex)\(rowIndex) \t")
                columArray.append(ticTocValueArray[rowIndex][colIndex])
            }
            print("\n")
            allCoulumArray.append(columArray)
        }
        
        self.lookForWinner(inputRowOrColumnArray: allCoulumArray)
    }
    
    func lookForWinner(inputRowOrColumnArray:[[Int]]) {
        guard !isWinnerFound else {
            return
        }
        for rowArr in inputRowOrColumnArray{
            let uniqueValueRowArray = Array(Set(rowArr))
            
            if uniqueValueRowArray.count == 1, let val = uniqueValueRowArray.first{
                switch val {
                case 1:
                    
                    var luigiWinCount = UserDefaults.standard.integer(forKey: "Luigi")
                    luigiWinCount = luigiWinCount + 1
                    UserDefaults.standard.set(luigiWinCount, forKey: "Luigi")
                    self.displayWinnerPopup(title: "Congratulation!", message: "Player One, Your Luigi has won")
                    break
                    
                case 0:
                    var marioWinCount = UserDefaults.standard.integer(forKey: "Mario")
                    marioWinCount = marioWinCount + 1
                    UserDefaults.standard.set(marioWinCount, forKey: "Mario")
                    self.displayWinnerPopup(title: "Congratulation!", message: "Player Two, Your Mario has won")
                    break
                    
                default:
                    break
                }
            }
        }
    }
    
    
    func displayWinnerPopup(title: String, message: String){
        playerOneView.setBorder(radius: 5.0)
        playerTwoView.setBorder(radius: 5.0)
        isWinnerFound = true
        presentAlertWithTitle(title: title, message: message, options: "New Game","Cancel") { (option) in
            print("option: \(option)")
            switch(option) {
            case 0:
                self.setUpNewGame()
                print("option one")
                break
            default:
                break
            }
        }
    }
    
    
    func setUpNewGame() {
        
        for (_, btn) in refButtonArray.enumerated(){
            btn.setTitle(" ", for: UIControl.State.normal)
        }
        refButtonArray.removeAll()
        self.isWinnerFound = false
        ticTocValueArray = Array(repeating: Array(repeating: -1, count: 3), count: 3)
        currentPlayer = PlayerRolev.Player1
        playerOneView.setBorder(radius: 5.0, color: UIColor.blue)
        playerTwoView.setBorder(radius: 5.0)
        
        let luigiWinCount = UserDefaults.standard.integer(forKey: "Luigi")
        let marioWinCount = UserDefaults.standard.integer(forKey: "Mario")
        
        playerOneWinCountLbl.text = "Luigi - \(luigiWinCount)"
        playerTwoWinCountLbl.text = "Mario - \(marioWinCount)"
        
    }
    
    
    @IBAction func newGameBtnAction(_ sender: Any) {
        setUpNewGame()
    }
    
    
}

