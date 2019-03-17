//
//  ViewController.swift
//  TicTacToe
//
//  Created by Naraharisetty, Venkat on 3/16/19.
//  Copyright © 2019 Naraharisetty, Venkat. All rights reserved.
//

import UIKit
import Lottie

class ViewController: UIViewController {

    @IBOutlet weak var oButton: UIButton!
    @IBOutlet weak var xButton: UIButton!
    @IBOutlet weak var welcomeAnimationView: LOTAnimationView!
    
    
    // ViewDidLoad-- make actions that are fairly static
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    // View will appear-- make actions that are fairly dynamic
    override func viewWillAppear(_ animated: Bool) {
        startWelcomeAnimation()
        startButtonAnimation()
    }
    
    // Function to animate buttons. This is created using button animations.
    func startButtonAnimation() {
        oButton.pulsate()
        xButton.pulsate()
    }
    // Welcome animation to display when user launches screen. This is created by Lotte framework using cocoapods
    func startWelcomeAnimation(){
        welcomeAnimationView.setAnimation(named:"lottie_welcome.json")
        welcomeAnimationView.loopAnimation = true
        welcomeAnimationView.play()
    }
    
    
    // Segue to Game Controller based on the button selected. We will pass the button flag so that we will know the user selected option
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        welcomeAnimationView.stop()
        if segue.identifier == "oButtonSegue"{
            if let gameViewController = segue.destination as? GameViewController {
                gameViewController.selectedButtonStyle = "O"
            }
        } else if segue.identifier == "xButtonSegue" {
            if let gameViewController = segue.destination as? GameViewController {
                gameViewController.selectedButtonStyle = "X"
            }
        }
    }
    
    
  
}

