//
//  TicTacCell.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/19/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit

enum PlayerIcon {
    case xIcon
    case oIcon
}

class TicTacCell: UICollectionViewCell {
    @IBOutlet weak var iconImage: UIImageView!
    
    //green version of the icon will be used to point out winning combination
    func configureCell(icon: PlayerIcon, winner: Bool) {
        switch icon {
        case .xIcon:
            iconImage.image = UIImage(named: winner ? "greenXicon" : "regularXicon")
        case .oIcon:
            iconImage.image = UIImage(named: winner ? "greenOicon" : "regularOicon")
        }
    }
    
    override func prepareForReuse() {
        iconImage.image = nil
    }
}
