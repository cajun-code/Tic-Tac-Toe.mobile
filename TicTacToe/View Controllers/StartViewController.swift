//
//  StartViewController.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit

protocol StartViewControllerDelegate {
    func xButtonSelected()
    func oButtonSelected()
    func statsButtonSelected()
}

class StartViewController: UIViewController {
    var delegate: StartViewControllerDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        //hides back button so user isn't able to access the rootVC
        navigationItem.hidesBackButton = true
        
        let statsButton = UIBarButtonItem.init(title: "Stats", style: .plain, target: self, action: #selector(statsButtonAction))
        self.navigationItem.rightBarButtonItem = statsButton
    }
    
    @IBAction func xButtonAction(_ sender: Any) {
        delegate?.xButtonSelected()
    }
    
    @IBAction func oButtonAction(_ sender: Any) {
        delegate?.oButtonSelected()
    }
    
    @objc func statsButtonAction() {
        delegate?.statsButtonSelected()
    }
}
