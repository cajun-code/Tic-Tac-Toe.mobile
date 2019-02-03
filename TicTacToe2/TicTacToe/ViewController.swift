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
    

    @IBOutlet weak var messageLabel: UILabel!
    
    
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
        
        AIChoice = ""
        playerChoice = ""
        
        //display the starting message
        messageLabel.text = "Hi There, You Go first.  Choose X or O to get started"
        
        //enable the buttons
        isButtonEnabled(isEnabled: true)
        
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
        
        //clear the previous arrays
        playerButtonTagArray.removeAll()
        aiButtonTagArray.removeAll()
        
        
    }

    @IBAction func chooseXTapped(_ sender: UIButton) {
        playerChoice = "X"
        AIChoice = "O"
        chX.isHidden = true
        chO.isHidden = true
        messageLabel.text = "Ah X it is! Tap on the tile of your choice to get started."
    }
    
    @IBAction func chooseOTapped(_ sender: UIButton) {
        playerChoice = "O"
        AIChoice = "X"
        chX.isHidden = true
        chO.isHidden = true
        messageLabel.text = "Ah O it is! Tap on the tile of your choice to get started."
    }
    
    
    @IBAction func ticTacToeTapped(_ sender: UIButton) {
        
        playerButtonTag = sender.tag
        
        sender.setTitle(playerChoice, for: .normal)
        
        //populate the array with the player buttontags to keep keep track of the player buttons tapped
        playerButtonTagArray.append(playerButtonTag)
        isButtonEnabled(isEnabled: false)
        aiPlays()
        
    
    }
    //MARK: Functions
    
    //function to enable or disable the buttons to avoid tapping more than one button at a time
    
    func isButtonEnabled(isEnabled: Bool) {
        if isEnabled == true {
            button0.isEnabled = true
            button0.isUserInteractionEnabled = true
            button1.isEnabled = true
            button1.isUserInteractionEnabled = true
            button2.isEnabled = true
            button2.isUserInteractionEnabled = true
            button3.isEnabled = true
            button3.isUserInteractionEnabled = true
            button4.isEnabled = true
            button4.isUserInteractionEnabled = true
            button5.isEnabled = true
            button5.isUserInteractionEnabled = true
            button6.isEnabled = true
            button6.isUserInteractionEnabled = true
            button7.isEnabled = true
            button7.isUserInteractionEnabled = true
            button8.isEnabled = true
            button8.isUserInteractionEnabled = true
        }
        else
        {
            button0.isEnabled = false
            button0.isUserInteractionEnabled = false
            button1.isEnabled = false
            button1.isUserInteractionEnabled = false
            button2.isEnabled = false
            button2.isUserInteractionEnabled = false
            button3.isEnabled = false
            button3.isUserInteractionEnabled = false
            button4.isEnabled = false
            button4.isUserInteractionEnabled = false
            button5.isEnabled = false
            button5.isUserInteractionEnabled = false
            button6.isEnabled = false
            button6.isUserInteractionEnabled = false
            button7.isEnabled = false
            button7.isUserInteractionEnabled = false
            button8.isEnabled = false
            button8.isUserInteractionEnabled = false
        
        }
        
        
    }
    
    
    func aiPlays() {
        //use the lastest choice and the previous choices to block the player from completing a line in any direction
        print("Player button tag array count in aiPlays func = \(playerButtonTagArray.count)")
        print("PlayerTagArray in AI Plays func values are: \(playerButtonTagArray)")
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
        case 2, 3, 4:
            //on AI's third turn and later which is also playerarray count > 2, start checking for the winning AI Combo
            if playerButtonTagArray.count > 2 {
                aiCheckWinningCombo()
            }
            //if the above winning combo is found AI wins and the game is over
            // otherwise the code below is executed.
            
            // For 2nd, 3rd, 4 th and 5th player turn check the blocking after the winning combo check is false
            // when the player has 2 marked tiles first block the player
            
            blockThirdTileCheck()
            
            //if ai unable to mark a tile due to no blocking or no winning scenerio then check AIcount and execute the code to mark an empty tile
            if playerButtonTagArray.count != aiButtonTagArray.count {
                if playerButtonTagArray.count > aiButtonTagArray.count {
                    noWinNoBlock()
                }
                else
                    //if AI marked more t han one tile to block pop the last one and erase the corresponding button title.
                {
                    while aiButtonTagArray.count > playerButtonTagArray.count {
                        let lastTag = aiButtonTagArray.popLast()
                        poplastTag(lastTag: lastTag!)
                    }
                    
                    
                }
                
            }
            
        case 5:
            // If AI didn't win on the fifth player turn then call it a draw
            
            messageLabel.text = "It's a Draw"
            //set the buttons as not enabled
            isButtonEnabled(isEnabled: false)
            //load the view after a 4 second delay to show the user the results before starting over again.
            DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(4)) {
                self.viewDidLoad()
            }
            
            
        default:
            break
        }
        print("AI button tag array count in aiPlays func = \(aiButtonTagArray.count)")
        print("AITagArray in AI Plays func values are: \(aiButtonTagArray)")
        
        
        
    }
    
    //function to see if ai array has any extra tags
    
    func poplastTag(lastTag: Int) {
        let arrButtons = [button0, button1, button2, button3, button4, button5, button6, button7, button8]
        for button in arrButtons {
            if button!.tag == lastTag {
                button?.setTitle("", for: .normal)
                isButtonEnabled(isEnabled: true)
                break
            }
        }
        
        
    }
    
    //this function is a scenerio when neither a win for AI is identified nor a Block needs to be made.  In that case AI looks for an empty tile to mark
    func noWinNoBlock() {
        let arrButtons = [button0, button1, button2, button3, button4, button5, button6, button7, button8]
        for button in arrButtons {
            if button!.currentTitle == "" {
                button!.setTitle(AIChoice, for: .normal)
                aiButtonTagArray.append(button!.tag)
                isButtonEnabled(isEnabled: true)
                break
            }

        }
        
        
    }
    
    
    func blockThirdTile(tag1: Int, tag2: Int, thirdTile: UIButton) {
        
        if thirdTile.currentTitle == "" && playerButtonTagArray.contains(tag1) &&  playerButtonTagArray.contains(tag2)  {
            //code for empty button
            thirdTile.setTitle(AIChoice, for: .normal)
            aiButtonTagArray.append(thirdTile.tag)
            isButtonEnabled(isEnabled: true)
        }
        
    }
    
    
    func blockThirdTileCheck() {
        
        //check the 8 possible solutions and block the third tile in the solution combination
        
        //block the third tile for 0,1,2 combination
            
        blockThirdTile(tag1: 0, tag2: 1, thirdTile: button2)
        blockThirdTile(tag1: 0, tag2: 2, thirdTile: button1)
        blockThirdTile(tag1: 1, tag2: 2, thirdTile: button0)
        
        //block the third tile for 0,3,6 combination
        
        blockThirdTile(tag1: 0, tag2: 3, thirdTile: button6)
        blockThirdTile(tag1: 0, tag2: 6, thirdTile: button3)
        blockThirdTile(tag1: 3, tag2: 6, thirdTile: button0)
        
        //block the third tile for 2,5,8 combination
        
        blockThirdTile(tag1: 2, tag2: 5, thirdTile: button8)
        blockThirdTile(tag1: 2, tag2: 8, thirdTile: button5)
        blockThirdTile(tag1: 5, tag2: 8, thirdTile: button2)
        
        //block the third tile for 6,7,8 combination
        
        blockThirdTile(tag1: 6, tag2: 7, thirdTile: button8)
        blockThirdTile(tag1: 7, tag2: 8, thirdTile: button6)
        blockThirdTile(tag1: 6, tag2: 8, thirdTile: button7)
        
        //block the third tile for 3,4,5 combination
            
        blockThirdTile(tag1: 3, tag2: 4, thirdTile: button5)
        blockThirdTile(tag1: 4, tag2: 5, thirdTile: button3)
        blockThirdTile(tag1: 3, tag2: 5, thirdTile: button4)
        
        
        //block the third tile for 1,4,7 combination
        blockThirdTile(tag1: 1, tag2: 4, thirdTile: button7)
        blockThirdTile(tag1: 4, tag2: 7, thirdTile: button1)
        blockThirdTile(tag1: 1, tag2: 7, thirdTile: button4)
        
        //when zero button tag is a player choice check and block the third tile for 0,4,8 combination
        blockThirdTile(tag1: 0, tag2: 4, thirdTile: button8)
        blockThirdTile(tag1: 4, tag2: 8, thirdTile: button0)
        blockThirdTile(tag1: 0, tag2: 8, thirdTile: button4)
        
        //block the third tile for 2,4,6 combination
        blockThirdTile(tag1: 2, tag2: 4, thirdTile: button6)
        blockThirdTile(tag1: 6, tag2: 4, thirdTile: button2)
        blockThirdTile(tag1: 2, tag2: 6, thirdTile: button4)
            
       
        
        
        
    }
    
    func aiWinningComboFound(aiTag1: Int, aiTag2: Int, tag3: Int, emptyThirdTile: UIButton)  {
        
        if aiButtonTagArray.contains(aiTag1) && aiButtonTagArray.contains(aiTag2) && !playerButtonTagArray.contains(tag3) && emptyThirdTile.currentTitle == "" {
            emptyThirdTile.setTitle(AIChoice, for: .normal)
            isButtonEnabled(isEnabled: false)
            aiButtonTagArray.append(emptyThirdTile.tag)
            
            messageLabel.text = "You Lose"
            
            //load the view after a 4 second delay to show the user the results before starting over again.
            DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(4)) {
                self.viewDidLoad()
            }
            
        }
        
        
    }
    
    func aiCheckWinningCombo() {
        //0,1,2 combination
        aiWinningComboFound(aiTag1: 0, aiTag2: 1, tag3: 2, emptyThirdTile: button2)
        aiWinningComboFound(aiTag1: 0, aiTag2: 2, tag3: 1, emptyThirdTile: button1)
        aiWinningComboFound(aiTag1: 1, aiTag2: 2, tag3: 0, emptyThirdTile: button0)
        
        //3,4,5 combination
        aiWinningComboFound(aiTag1: 3, aiTag2: 4, tag3: 5, emptyThirdTile: button5)
        aiWinningComboFound(aiTag1: 3, aiTag2: 5, tag3: 4, emptyThirdTile: button4)
        aiWinningComboFound(aiTag1: 4, aiTag2: 5, tag3: 3, emptyThirdTile: button3)
        
        //6,7,8 combination
        aiWinningComboFound(aiTag1: 6, aiTag2: 7, tag3: 8, emptyThirdTile: button8)
        aiWinningComboFound(aiTag1: 6, aiTag2: 8, tag3: 7, emptyThirdTile: button7)
        aiWinningComboFound(aiTag1: 7, aiTag2: 8, tag3: 6, emptyThirdTile: button6)
        
        //0,3,6 combination
        aiWinningComboFound(aiTag1: 0, aiTag2: 3, tag3: 6, emptyThirdTile: button6)
        aiWinningComboFound(aiTag1: 0, aiTag2: 6, tag3: 3, emptyThirdTile: button3)
        aiWinningComboFound(aiTag1: 3, aiTag2: 6, tag3: 0, emptyThirdTile: button0)
        
        //1,4,7 combination
        aiWinningComboFound(aiTag1: 1, aiTag2: 4, tag3: 7, emptyThirdTile: button7)
        aiWinningComboFound(aiTag1: 1, aiTag2: 7, tag3: 4, emptyThirdTile: button4)
        aiWinningComboFound(aiTag1: 4, aiTag2: 7, tag3: 1, emptyThirdTile: button1)
        
        //2,5,8
        aiWinningComboFound(aiTag1: 2, aiTag2: 5, tag3: 8, emptyThirdTile: button8)
        aiWinningComboFound(aiTag1: 2, aiTag2: 8, tag3: 5, emptyThirdTile: button5)
        aiWinningComboFound(aiTag1: 5, aiTag2: 8, tag3: 2, emptyThirdTile: button2)
        
        //0,4,8
        aiWinningComboFound(aiTag1: 0, aiTag2: 4, tag3: 8, emptyThirdTile: button8)
        aiWinningComboFound(aiTag1: 0, aiTag2: 8, tag3: 4, emptyThirdTile: button4)
        aiWinningComboFound(aiTag1: 4, aiTag2: 8, tag3: 0, emptyThirdTile: button0)
        
        //2,4,6
        aiWinningComboFound(aiTag1: 2, aiTag2: 4, tag3: 6, emptyThirdTile: button6)
        aiWinningComboFound(aiTag1: 2, aiTag2: 6, tag3: 4, emptyThirdTile: button4)
        aiWinningComboFound(aiTag1: 4, aiTag2: 6, tag3: 2, emptyThirdTile: button2)
        
    }
    
    

}


