//
//  StatsViewController.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit

protocol StatsViewControllerDelegate {
    func finishedViewingStats()
}

class StatsViewController: UIViewController {
    @IBOutlet weak var winsTextField: UITextField!
    @IBOutlet weak var tiesTextField: UITextField!
    @IBOutlet weak var lossesTextField: UITextField!
    var delegate: StatsViewControllerDelegate?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        getStats()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        delegate?.finishedViewingStats()
    }
    
    func getStats() {
        guard let stats = StatTracker.getStats() else {
            log.error("Unable to get stats from database")
            return
        }
        
        winsTextField.text = "\(stats.wins)"
        tiesTextField.text = "\(stats.ties)"
        lossesTextField.text = "\(stats.losses)"
    }
}
