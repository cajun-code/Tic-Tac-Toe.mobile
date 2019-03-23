//
//  GameViewController.swift
//  Tic-Tao-Toe.mobile
//
//  Created by Anirudh Kanathala on 3/22/19.
//  Copyright © 2019 kanathala. All rights reserved.
//

import UIKit
import SpriteKit
import GameplayKit

class GameViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        // loading the Game scene
        if let view = self.view as! SKView? {
            let scene = GameScene(fileNamed: "GameScene")
            view.presentScene(scene)
        }
        
    }

}
