//
//  Cell.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import UIKit

class Cell: UIButton {
    var setBy = CellOwner.none {
        didSet {
            updateUI()
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        self.titleLabel?.font = UIFont.systemFont(ofSize: 36)
        self.layer.borderWidth = 1
        self.layer.borderColor = UIColor.black.cgColor
        
        updateUI()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func updateUI() {
        self.setTitle(setBy.textual(), for: .normal)
    }
}
