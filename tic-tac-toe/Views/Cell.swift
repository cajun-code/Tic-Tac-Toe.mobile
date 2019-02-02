//
//  Cell.swift
//  tic-tac-toe
//
//  Created by Yevhen Strohanov on 2/2/19.
//  Copyright © 2019 Organization Name. All rights reserved.
//

import UIKit

class Cell: UIButton {
    var actionBlock: (() -> ())?
    
    var setBy = CellOwner.none {
        didSet {
            updateUI()
        }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        self.titleLabel?.font = UIFont.systemFont(ofSize: 36)
        self.setTitleColor(.black, for: .normal)
        self.layer.borderWidth = 1
        self.layer.borderColor = UIColor.black.cgColor
        
        updateUI()
        
        addTarget(self, action: #selector(tapped), for: .touchUpInside)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    @objc func tapped() {
        if setBy == .none {
            self.actionBlock?()
            self.setBy = .player
        }
    }
    
    func updateUI() {
        self.setTitle(setBy.textual(), for: .normal)
    }
}
