//
//  GameViewController.swift
//  Tic-Tac-Toe.mobile
//
//  Created by Akshay Pimprikar on 2/7/19.
//  Copyright © 2019 Akshay Pimprikar. All rights reserved.
//

import UIKit

class GameViewController: UIViewController {

    var newGameButton = UIBarButtonItem()

    var gameBoardView: UICollectionView = {
        let layout = UICollectionViewFlowLayout()
        layout.itemSize = CGSize(width: 108.0, height: 109.0)
        layout.minimumLineSpacing = 10.0
        layout.minimumInteritemSpacing = 10.0
        layout.sectionInset = UIEdgeInsets(top: 0.0, left: 0.0, bottom: 5.0, right: 0.0)
        layout.scrollDirection = .vertical
        let board = UICollectionView(frame: .zero, collectionViewLayout: layout)
        board.backgroundColor = .lightGray
        board.isScrollEnabled = false
        return board
    }()

    var currentStatusLabel: UILabel = {
        let currentLabel = UILabel()
        currentLabel.font = .systemFont(ofSize: 20.0)
        currentLabel.translatesAutoresizingMaskIntoConstraints = false
        return currentLabel
    }()

    var currentPlayerImage: UIImageView = {
        let currentImage = UIImageView()
        currentImage.translatesAutoresizingMaskIntoConstraints = false
        return currentImage
    }()

    var board = Board()
    var humanPlayer = Player()
    var aiPlayer = Ai()
    var gameOver = false {
        didSet {
            gameBoardView.allowsSelection = !gameOver
        }
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        configureSubviews()
        startGame()
    }

    func configureSubviews() {
        navigationItem.title = Constants.titleString
        navigationItem.hidesBackButton = true
        view.backgroundColor = .white

        newGameButton = UIBarButtonItem(title: Constants.newGameTitle, style: .plain, target: self, action: #selector(newGameTapped))
        navigationItem.rightBarButtonItem = newGameButton

        view.addSubview(gameBoardView)
        gameBoardView.dataSource = self
        gameBoardView.delegate = self
        gameBoardView.register(BoardCell.self, forCellWithReuseIdentifier: Constants.cellIdentifier)
        gameBoardView.translatesAutoresizingMaskIntoConstraints = false
        gameBoardView.widthAnchor.constraint(equalToConstant: 345.0).isActive = true
        gameBoardView.heightAnchor.constraint(equalToConstant: 336.0).isActive = true
        gameBoardView.centerXAnchor.constraint(equalTo: view.safeAreaLayoutGuide.centerXAnchor).isActive = true
        gameBoardView.centerYAnchor.constraint(equalTo: view.safeAreaLayoutGuide.centerYAnchor).isActive = true

        view.addSubview(currentPlayerImage)
        currentPlayerImage.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor, constant: 144.0).isActive = true
        currentPlayerImage.bottomAnchor.constraint(equalTo: gameBoardView.topAnchor, constant: -27.0).isActive = true
        currentPlayerImage.heightAnchor.constraint(equalToConstant: 24.0).isActive = true
        currentPlayerImage.widthAnchor.constraint(equalToConstant: 24.0).isActive = true

        view.addSubview(currentStatusLabel)
        currentStatusLabel.leadingAnchor.constraint(equalTo: currentPlayerImage.trailingAnchor, constant: 3.0).isActive = true
        currentStatusLabel.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor, constant: 142.5).isActive = true
        currentStatusLabel.bottomAnchor.constraint(equalTo: gameBoardView.topAnchor, constant: -23.0).isActive = true
    }

    @objc func newGameTapped() {
        navigationController?.popViewController(animated: true)
        dismiss(animated: true, completion: nil)
    }

    func startGame() {
        board.gameBoardArray = Array(repeating: "", count: 9)
        gameBoardView.reloadData()
        gameOver = false

        if humanPlayer.playerType == .O {
            aiPlayer.playerType = .X
        } else {
            aiPlayer.playerType = .O
        }
        let playerString = humanPlayer.playerType?.rawValue ?? ""
        board.currentTurn = playerString
        currentPlayerImage.image = UIImage(named: playerString)
        currentStatusLabel.text = Constants.playerTurnString
    }

    func aiMakeMove() {
        let index = aiPlayer.findBestMove(board)
        board.gameBoardArray[index] = aiPlayer.playerType?.rawValue ?? ""
        gameBoardView.reloadData()
        checkGameStatus()
    }

    func togglePlayer() {
        if board.currentTurn == humanPlayer.playerType?.rawValue {
            let playerString = aiPlayer.playerType?.rawValue ?? ""
            board.currentTurn = playerString
            currentPlayerImage.image = UIImage(named: playerString)
            gameBoardView.allowsSelection = false
            DispatchQueue.main.asyncAfter(deadline: .now() + .milliseconds(300)) {
                self.aiMakeMove()
            }
        } else {
            let playerString = humanPlayer.playerType?.rawValue ?? ""
            board.currentTurn = playerString
            currentPlayerImage.image = UIImage(named: playerString)
            gameBoardView.allowsSelection = true
        }
    }

    func checkGameStatus() {
        switch board.gameStatus() {
        case .InProgress:
            togglePlayer()
        case .Draw:
            currentPlayerImage.image = nil
            currentStatusLabel.text = Constants.gameDraw
        case .Won:
            currentStatusLabel.text = Constants.playerWonString
        }
    }
}

extension GameViewController: UICollectionViewDataSource {
    func numberOfSections(in collectionView: UICollectionView) -> Int {
        return 1
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 9
    }

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: Constants.cellIdentifier, for: indexPath) as? BoardCell else { fatalError("Couldn't dequeue cell") }
        let imageString = board.gameBoardArray[indexPath.row]

        if imageString.isEmpty {
            cell.boardCellImageView.image = nil
        } else {
            cell.boardCellImageView.image = UIImage(named: imageString)
        }
        return cell
    }
}

extension GameViewController: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let cell = collectionView.cellForItem(at: indexPath) as? BoardCell
        if cell?.boardCellImageView.image == nil {
            board.gameBoardArray[indexPath.row] = board.currentTurn
            cell?.boardCellImageView.image = UIImage(named: board.currentTurn)

            checkGameStatus()
        }
    }
}
