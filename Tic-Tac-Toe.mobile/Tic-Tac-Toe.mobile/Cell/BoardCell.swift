//
//  BoardCell.swift
//  Tic-Tac-Toe.mobile
//
//  Created by Akshay Pimprikar on 2/7/19.
//  Copyright © 2019 Akshay Pimprikar. All rights reserved.
//

import UIKit

class BoardCell: UICollectionViewCell {

    var boardCellImageView = UIImageView()

    override init(frame: CGRect) {
        super.init(frame: frame)
        backgroundColor = .white
        addSubview(boardCellImageView)

        boardCellImageView.translatesAutoresizingMaskIntoConstraints = false
        boardCellImageView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: 7.0).isActive = true
        boardCellImageView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -5.0).isActive = true
        boardCellImageView.topAnchor.constraint(equalTo: topAnchor, constant: 7.0).isActive = true
        boardCellImageView.bottomAnchor.constraint(equalTo: bottomAnchor, constant: -7.0).isActive = true
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
