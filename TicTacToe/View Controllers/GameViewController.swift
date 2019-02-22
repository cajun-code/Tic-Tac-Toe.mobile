//
//  GameViewController.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/19/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import UIKit

protocol GameViewControllerDelegate {
    func gameCompleted()
}

class GameViewController: UIViewController {
    
    @IBOutlet weak var statusTextField: UITextField!
    @IBOutlet weak var collectionView: UICollectionView!
    var viewModel = ViewControllerVM()
    var delegate: GameViewControllerDelegate?
    var userIcon = PlayerIcon.oIcon
    
    override func viewDidLoad() {
        super.viewDidLoad()
        viewModel.delegate = self
        collectionView.delegate = self
        collectionView.dataSource = self
        collectionView.register(UINib(nibName: "TicTacCell", bundle: nil), forCellWithReuseIdentifier: "TicTacCell")
        collectionView.backgroundColor = .black
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        delegate?.gameCompleted()
    }

    @IBAction func resetButtonAction(_ sender: Any) {
        statusTextField.text = ""
        viewModel.resetGame()
        collectionView.reloadData()
    }
}

//MARK: ViewControllerVMDelegate
extension GameViewController: ViewControllerVMDelegate {
    //update status text fied based on view model data
    func setStatus(text: String) {
        statusTextField.text = text
        statusTextField.sizeToFit()
    }
    
    func refreshData(indexes: [IndexPath]?) {
        guard let indexesToUpdate = indexes else {
            //update all
            let numberOfItems = collectionView.numberOfItems(inSection: 0)
            let indexPaths = [Int](0..<numberOfItems).map { IndexPath(row: $0, section: 0) }
            collectionView.reloadItems(at: indexPaths)
            return
        }
        
        self.collectionView.reloadItems(at: indexesToUpdate)
    }
}

//MARK: UICollectionView
extension GameViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 9
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        guard let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "TicTacCell", for: indexPath) as? TicTacCell else {
            return UICollectionViewCell()
        }
        
        if viewModel.userSelections.contains(indexPath.row) {
            //show icon for user preference
            cell.configureCell(icon: userIcon, winner: viewModel.winningCombination.contains(indexPath.row))
        } else if viewModel.computerSelections.contains(indexPath.row) {
            let computerIcon = (userIcon == .oIcon) ? PlayerIcon.xIcon : PlayerIcon.oIcon
            cell.configureCell(icon: computerIcon, winner: viewModel.winningCombination.contains(indexPath.row))
        }
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView,
                        layout collectionViewLayout: UICollectionViewLayout,
                        sizeForItemAt indexPath: IndexPath) -> CGSize {
        //set collection view cells to fill the 6 cells and fill the screen
        let height = self.view.frame.size.height
        let width = self.view.frame.size.width
        return CGSize.init(width: width * 0.3, height: height * 0.28)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        guard !viewModel.userSelections.contains(indexPath.row) || !viewModel.computerSelections.contains(indexPath.row) else {
            log.info("there's a previous selection for this row")
            return
        }
        
        viewModel.userSelections.append(indexPath.row)
        collectionView.reloadItems(at: [indexPath])
        
        guard !viewModel.checkIfGameIsOver() else {
            //game is over, no need to continue
            return
        }
        
        //start the move for the computer
        viewModel.beginComputerMove()
    }
}

