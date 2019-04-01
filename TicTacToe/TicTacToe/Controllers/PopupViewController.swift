//
//  ViewController.swift
//  TicTacToe
//
//  Created by Arihant Arora on 3/28/19.
//  Copyright © 2019 Arihant Arora. All rights reserved.
//

import UIKit


protocol SymbolDelegate {
    func selectedSymbol(symbol : String)
}

class PopupViewController: UIViewController {

    @IBOutlet weak var topLbl: UILabel!
    @IBOutlet weak var popupView: UIView!
    @IBOutlet weak var circleBtn: UIButton!
    @IBOutlet weak var crossBtn: UIButton!
    
    var selectedAvatar = ""
    var delegate: SymbolDelegate?
    let scaleFactor: CGFloat = UIScreen.main.bounds.width/375
    
    override func viewDidLoad() {
        super.viewDidLoad()
        commonInit()
        // Do any additional setup after loading the view.
    }
    
    func commonInit(){
        
        dropShadow(view: popupView)
        topLbl.font = topLbl.font.withSize(20 * scaleFactor)
    }
    
    //To drop shadow for main view
    func dropShadow(scale: Bool = true, view: UIView) {
        
        view.layer.masksToBounds = false
        view.layer.shadowColor = UIColor.black.cgColor
        view.layer.shadowOpacity = 0.5
        view.layer.shadowOffset = CGSize(width: 0, height: 2)
        view.layer.shadowRadius = 4
        
    }


    @IBAction func pressedCircle(_ sender: Any) {
        
        selectedAvatar = "circle"
        if delegate != nil {
            delegate?.selectedSymbol(symbol: selectedAvatar)
            
            self.dismiss(animated: true, completion: nil)
        }
        
    }
    
    @IBAction func pressedCross(_ sender: Any) {
        
        selectedAvatar = "cross"
        if delegate != nil {
            delegate?.selectedSymbol(symbol: selectedAvatar)
            self.dismiss(animated: true, completion: nil)
            
        }
    }
    
}
