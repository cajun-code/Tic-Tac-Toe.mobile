//
//  ViewController.swift
//  Tic-Tao-Toe.mobile
//
//  Created by Anirudh Kanathala on 3/22/19.
//  Copyright © 2019 kanathala. All rights reserved.
//

import UIKit

class PlayerSelectionViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func onTap(_ sender: Any) {
        let defaults = UserDefaults.standard
        
        if (sender as AnyObject).tag == 1 {
            defaults.set("circle", forKey: "piece")
        }else{
            defaults.set("cross", forKey: "piece")
        }
        performSegue(withIdentifier: "gameVC", sender: (sender as AnyObject).tag)
        
    }
}

