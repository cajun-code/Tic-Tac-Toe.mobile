//
//  ViewController.swift
//  TicTacToe
//
//  Created by Sridevi Panyam on 1/30/19.
//  Copyright © 2019 SP. All rights reserved.
//

import UIKit




class ViewController: UIViewController {
    var AIChoice = String()
    var playerChoice = String()
    var playerButtonTag = Int()
    var playerButtonTagArray = [Int]()
    var aiButtonTagArray = [Int]()
    
    //array variables for the 9 possible solutions
    var hSol1 = [0,1,2]
    var hSol2 = [3,4,5]
    var hSol3 = [6,7,8]
    
    var vSol1 = [0,3,6]
    var vSol2 = [1,4,7]
    var vSol3 = [2,5,8]
    
    var dSol1 = [0,4,8]
    var dSol2 = [2,4,6]
    
    @IBOutlet weak var chX: UIButton!
    
    @IBOutlet weak var chO: UIButton!
    
    @IBOutlet weak var button0: UIButton!
    
    @IBOutlet weak var button1: UIButton!
    
    @IBOutlet weak var button2: UIButton!
    
    @IBOutlet weak var button3: UIButton!
    
    @IBOutlet weak var button4: UIButton!
    
    @IBOutlet weak var button5: UIButton!
    
    @IBOutlet weak var button6: UIButton!
    
    @IBOutlet weak var button7: UIButton!
    
    @IBOutlet weak var button8: UIButton!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        //unhide the selection buttons when first loaded
        
        chX.isHidden = false
        chO.isHidden = false
        //clear any title on the buttons when loaded
        
        button0.setTitle("", for: .normal)
        button1.setTitle("", for: .normal)
        button2.setTitle("", for: .normal)
        button3.setTitle("", for: .normal)
        button4.setTitle("", for: .normal)
        button5.setTitle("", for: .normal)
        button6.setTitle("", for: .normal)
        button7.setTitle("", for: .normal)
        button8.setTitle("", for: .normal)
        
    }

    @IBAction func chooseXTapped(_ sender: UIButton) {
        playerChoice = "X"
        AIChoice = "O"
        chX.isHidden = true
        chO.isHidden = true
        
    }
    
    @IBAction func chooseOTapped(_ sender: UIButton) {
        playerChoice = "O"
        AIChoice = "X"
        chX.isHidden = true
        chO.isHidden = true
    }
    
    
    @IBAction func ticTacToeTapped(_ sender: UIButton) {
        
        playerButtonTag = sender.tag
        print(playerButtonTag)
        sender.setTitle(playerChoice, for: .normal)
        //populate teh array with the player buttontags to keep keep track of the player buttons tapped
        playerButtonTagArray.append(playerButtonTag)
        isButtonEnabled(isEnabled: false)
        aiplays()
        
    }
    
    //function to enable or disable the buttons to avoid tapping more than one button at a time
    
    func isButtonEnabled(isEnabled: Bool) {
        if isEnabled == true {
            button0.isEnabled = true
            button1.isEnabled = true
            button2.isEnabled = true
            button3.isEnabled = true
            button4.isEnabled = true
            button5.isEnabled = true
            button6.isEnabled = true
            button7.isEnabled = true
            button8.isEnabled = true
        }
        else
        {
            button0.isEnabled = false
            button1.isEnabled = false
            button2.isEnabled = false
            button3.isEnabled = false
            button4.isEnabled = false
            button5.isEnabled = false
            button6.isEnabled = false
            button7.isEnabled = false
            button8.isEnabled = false
        }
        
        
    }
    
    func aiplays() {
        //use the lastest choice and the previous choices to block the player from completing a line in any direction
        
        switch playerButtonTagArray.count {
        case 1:
            //this is players first move
            if playerButtonTag != 4 {
                button4.setTitle(AIChoice, for: .normal)
                aiButtonTagArray.append(button4.tag)
            }
            else
            {
                button0.setTitle(AIChoice, for: .normal)
                aiButtonTagArray.append(button0.tag)
            }
            isButtonEnabled(isEnabled: true)
        case 2, 3, 4, 5:
            // this is the second tap by the player
            //find out if they tapped the center button or not previpously
            if aiButtonTagArray.contains(4) {
                //center button taken by AI
                //when zero is a player choice check to block the third tile
                if playerButtonTag == 0 && playerButtonTagArray.contains(1) {
                    button2.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(2)
                }
                if playerButtonTag == 0 && playerButtonTagArray.contains(2) {
                    button1.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(1)
                }
                if playerButtonTag == 0 &&  playerButtonTagArray.contains(3) {
                    button6.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(6)
                }
                if playerButtonTag == 0 &&  playerButtonTagArray.contains(6) {
                    button3.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(3)
                }
                
                //when one is a player choice those options have been  yackled already
                
                //when two is a player option only one combinatio left to tackle 2,5,8
                if playerButtonTag == 2 &&  playerButtonTagArray.contains(5) {
                    button8.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(8)
                }
                
                if playerButtonTag == 2 &&  playerButtonTagArray.contains(8) {
                    button5.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(5)
                }
                
                //when three is a player choice those options have been tackled already
                
                //when four is a player choice those options have been tackled already in the else statement below
                
                //when five is a player choice those options have been tackled already
                
                //when six is an player option the cobination left to tackle is 6,7,8
                
                if playerButtonTag == 6 &&  playerButtonTagArray.contains(7) {
                    button8.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(8)
                }
                
                if playerButtonTag == 6 &&  playerButtonTagArray.contains(8) {
                    button7.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(7)
                }
                
                //when button tag seven is a player choice those options have been tackled already
                
                //when button tag eight isd a player choice those options have been tackled already
                
                
            }
            else
            {
                //center button taken by player check the 8 possible posultions and block the third tile ion the solution
                
                if playerButtonTagArray.contains(3) {
                    button5.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(5)
                }
                if playerButtonTagArray.contains(5) {
                    button3.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(3)
                }
                if playerButtonTagArray.contains(1) {
                    button7.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(7)
                }
                if playerButtonTagArray.contains(7) {
                    button1.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(1)
                }
                if playerButtonTagArray.contains(0) {
                    button8.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(8)
                }
                if playerButtonTagArray.contains(8) {
                    button0.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(0)
                }
                if playerButtonTagArray.contains(2) {
                    button6.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(6)
                }
                if playerButtonTagArray.contains(6) {
                    button2.setTitle(AIChoice, for: .normal)
                    aiButtonTagArray.append(2)
                }
                
                
            }
            isButtonEnabled(isEnabled: true)
            
        default:
            break
        }
        
        
    }
    
    
    
}

