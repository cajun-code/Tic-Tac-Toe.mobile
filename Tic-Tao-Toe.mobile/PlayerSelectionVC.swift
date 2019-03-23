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
            defaults.set("circle", forKey: "type")
        }else{
            defaults.set("cross", forKey: "type")
        }
        performSegue(withIdentifier: "GameVC", sender: (sender as AnyObject).tag)
        
    }
    @IBAction func onCrossTap(_ sender: Any) {
        
    }
}

