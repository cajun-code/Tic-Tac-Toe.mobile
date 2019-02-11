//
//  ViewController.swift
//  Tic-Tac-Toe.mobile
//
//  Created by Akshay Pimprikar on 2/7/19.
//  Copyright © 2019 Akshay Pimprikar. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var pickLabel: UILabel = {
        let pickSymbolLabel = UILabel()
        pickSymbolLabel.font = .systemFont(ofSize: 20.0)
        pickSymbolLabel.translatesAutoresizingMaskIntoConstraints = false
        return pickSymbolLabel
    }()

    var oButton: UIButton = {
        let button = UIButton()
        button.setBackgroundImage(UIImage(named: "O"), for: .normal)
        button.tag = 1
        button.addTarget(self, action: #selector(playerSymbolPicked), for: .touchUpInside)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button
    }()

    var xButton: UIButton = {
        let button = UIButton()
        button.setBackgroundImage(UIImage(named: "X"), for: .normal)
        button.tag = 2
        button.addTarget(self, action: #selector(playerSymbolPicked), for: .touchUpInside)
        button.translatesAutoresizingMaskIntoConstraints = false
        return button

    }()

    override func viewDidLoad() {
        super.viewDidLoad()
        configureSubviews()
    }

    func configureSubviews() {
        navigationItem.title = Constants.titleString
        navigationController?.navigationBar.barTintColor = .lightGray
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor.white]
        navigationItem.hidesBackButton = true
        view.backgroundColor = .white

        pickLabel.text = Constants.pickLabelTitle
        view.addSubview(pickLabel)
        pickLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        pickLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 170.0).isActive = true

        view.addSubview(oButton)
        oButton.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 88.0).isActive = true
        oButton.widthAnchor.constraint(equalToConstant: 80.0).isActive = true
        oButton.heightAnchor.constraint(equalToConstant: 80.0).isActive = true
        oButton.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true

        view.addSubview(xButton)
        xButton.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: -88.0).isActive = true
        xButton.widthAnchor.constraint(equalToConstant: 80.0).isActive = true
        xButton.heightAnchor.constraint(equalToConstant: 80.0).isActive = true
        xButton.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
    }

    @objc private func playerSymbolPicked(sender: UIButton) {
        let gameViewController = GameViewController()
        if sender.tag == 1 {
            gameViewController.humanPlayer.playerType = .O
        } else {
            gameViewController.humanPlayer.playerType = .X
        }
        navigationController?.pushViewController(gameViewController, animated: true)
    }
}

