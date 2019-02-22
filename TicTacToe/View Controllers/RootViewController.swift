//
//  RootViewController.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit
import RealmSwift

protocol RootViewControllerDelegate: class {
    func presentHome(_ rootVC: RootViewController)
}

class RootViewController: UIViewController {
    weak var delegate: RootViewControllerDelegate?
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //prints out location of realm database (only works when running on simulator)
        log.info("realm is: \(String(describing: Realm.Configuration.defaultConfiguration.fileURL))")
        
        //just to demonstrate in UI that this UIViewController embeded in a nav controller is the root at app launch
        view.backgroundColor = UIColor.red
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        delegate?.presentHome(self)
    }
}
