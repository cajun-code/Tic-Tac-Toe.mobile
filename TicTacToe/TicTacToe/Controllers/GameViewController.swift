//
//  GameViewController.swift
//  TicTacToe
//
//  Created by Arihant Arora on 3/28/19.
//  Copyright © 2019 Arihant Arora. All rights reserved.
//

import UIKit
import AudioToolbox

//Delegate interaction from popup viewcontroller based on user's avatar choices
class GameViewController: UIViewController, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout , UICollectionViewDataSource, SymbolDelegate {
    
    
    @IBOutlet weak var collectionView: UICollectionView!
    @IBOutlet weak var resetButton: UIButton!
    @IBOutlet weak var AIMoveLoading: UIImageView!
    @IBOutlet weak var loadingPicHeightConstraint: NSLayoutConstraint!
    @IBOutlet weak var collectionviewTopConstraint: NSLayoutConstraint!
    @IBOutlet weak var resetBtnBottomConstraint: NSLayoutConstraint!
    
    //Scale Factor variable to handle constraints and attributes according to screen size
    let scaleFactor: CGFloat = UIScreen.main.bounds.width/375
    
//    _____________
//    | 0 | 1 | 2 |
//    | 3 | 4 | 5 |
//    | 6 | 7 | 8 |
//    -------------
    var possibleCells = [0,1,2,3,4,5,6,7,8]
    var possibleWinSituations = [[0,1,2],[0,4,8],[3,4,5],[6,7,8],[0,3,6],[1,4,7],[2,5,8],[2,4,6]]
    let cellsPerRow: Int = 3
    let spaceBetweenRows: CGFloat = 10.0
    
    //Order of preference: This is not the best way to do it but other ways would just be plagirizing
    var orderOfBestMoves = [4,0,2,6,8]
    
    //Avatar data updates after selection on PopupVC
    var selectedAvatar = "none"
    var userCell = false
    var AIAvatar: String!
    var AIMoves = [Int]()
    var playerMoves = [Int]()
    var AIImage = UIImage()
    var humanImage = UIImage()
    var cellSize = CGSize()
    var collectionViewHeight = CGFloat()
    var collectionViewWidth = CGFloat()
    var firstLaunch = true
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        commonInit()
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(true)
        //Go to popup if first launch
        if firstLaunch{
        DispatchQueue.main.asyncAfter(deadline: .now() + 1, execute: {
                self.performSegue(withIdentifier: "showSegue", sender: Any?.self)
            })
            
        }
        
    }
    
    func selectedSymbol(symbol: String) {
        
        firstLaunch = false
        resetButton.isUserInteractionEnabled = true
        resetButton.backgroundColor = #colorLiteral(red: 0.5764705882, green: 0.05882352941, blue: 0.1294117647, alpha: 1)
        selectedAvatar = symbol
        collectionView.isUserInteractionEnabled = true
        
        //Update AI and user chosen symbols based on what they decided in the popup
        switch selectedAvatar {
        case "cross":
            AIAvatar = "circle"
            AIImage = UIImage(named: "circle")!
            humanImage = UIImage(named: "cross")!
        case "circle":
            AIAvatar = "cross"
            AIImage = UIImage(named: "cross")!
            humanImage = UIImage(named: "circle")!
        default:
            AIAvatar = "none"
        }
        
        resetBoard()
    }
    
    func commonInit(){
       
        //Not letting user access button before the popup loads graciously
        if firstLaunch{
            resetButton.isUserInteractionEnabled = false
            resetButton.backgroundColor = .gray
        }
        
        collectionView.isUserInteractionEnabled = false
        
        //Calculate collectionview dimensions
        collectionViewWidth = (UIScreen.main.bounds.width * 0.914667) - (spaceBetweenRows * CGFloat(cellsPerRow)) * scaleFactor
        collectionViewHeight = collectionViewWidth * 1.12
        cellSize = CGSize(width: collectionViewWidth / CGFloat(cellsPerRow), height: collectionViewHeight / CGFloat(cellsPerRow))
        
        AIMoveLoading.isHidden = true
        resetButton.titleLabel?.font = UIFont(name: "MarkerFelt-Wide", size: 18 * scaleFactor)
        
        //Init constraints based on screen size
        loadingPicHeightConstraint.constant = 60 * scaleFactor
        collectionviewTopConstraint.constant *= scaleFactor
        resetBtnBottomConstraint.constant *= scaleFactor
    }
    
    // MARK: - Navigation

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        collectionView.isUserInteractionEnabled = false
        if segue.identifier == "showSegue" {
            let vc : PopupViewController = segue.destination as! PopupViewController
            vc.delegate = self

        }
    }
    
    func resetBoard() {
        
        //Empty for using again during append
        userCell = false
        AIMoves = []
        playerMoves = []
        
        collectionView.reloadData()
        
    }
    
    func showLoadingIcon(){
        //Show emoji which indicates that it is the computer's move
        AIMoveLoading.isHidden = false
        self.loadingPicHeightConstraint.constant = 100 * self.scaleFactor
        
        UIView.animate(withDuration: 0.5) {
            self.view.layoutIfNeeded()
        }

        self.loadingPicHeightConstraint.constant = 60 * self.scaleFactor
        
    }
    

    // MARK: - COLLECTION VIEW DESIGN
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        return possibleCells.count
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        
        return cellSize
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return spaceBetweenRows * scaleFactor
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return spaceBetweenRows * scaleFactor
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath) as! BoardCollectionViewCell
        //Init cell
        cell.isUserInteractionEnabled = true
        cell.backgroundColor = .white
        //nil everytime cells are generated afresh
        cell.imgView.image = nil
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        let cell = collectionView.cellForItem(at: indexPath) as! BoardCollectionViewCell
        debugPrint(print(indexPath))
        
        //Check to see if it is user or AI's turn
        if userCell {
            collectionView.isUserInteractionEnabled = false
            showLoadingIcon()
        DispatchQueue.main.asyncAfter(deadline: .now() + 1, execute:{
            
                cell.imgView.image = self.AIImage
                self.AIMoves.append(indexPath.row)
                self.userCell = false
                if self.AIMoves.count > 2 {
                    if AIBrain().Win(Moves: self.AIMoves)  {
                        self.showAlert(withString: "Computer won! Better luck next time!")
                    }
                }
            self.AIMoveLoading.isHidden = true
            collectionView.isUserInteractionEnabled = true
            })
        }
        else
        {
            cell.imgView.image = self.humanImage
            self.userCell = true
            self.playerMoves.append(indexPath.row)
            var didHumanWin = false
            if self.playerMoves.count > 2 {
                if  AIBrain().Win(Moves: self.playerMoves) {
                    self.showAlert(withString: "Congratulations! You achieved the impossible.")
                    didHumanWin = true
                }
            }
            //Check to see if human won already
            if !didHumanWin {
                let temp = NSMutableArray()
                let allMovesCombined = NSMutableArray(array: Array(self.AIMoves) + Array(self.playerMoves))
                temp.addObjects(from: self.possibleCells)
                for i in 0 ..< self.possibleCells.count
                {
                    if allMovesCombined.contains(self.possibleCells[i]) {
                        temp.remove(self.possibleCells[i])
                    }
                }
                
                //Figure out the best move to block the user's win. This is not minimax so it won't prioritize it's own win. Focuses more on not losing.
                
                var bestMove : Int?
                
                //Goes through possible moves in order of preference
                for value in self.playerMoves.filter({$0 != indexPath.row}){
                    for winSituations in self.possibleWinSituations{
                        if winSituations.contains(value) && winSituations.contains(indexPath.row){
                            for nextMove in winSituations.filter({temp.contains($0)}){
                                bestMove = nextMove
                                break
                            }
                        }
                    }
                    if (bestMove != nil){
                        break
                    }
                }
                if (bestMove == nil){
                    //Pick best move if one has not been done yet
                    bestMove = orderOfBestMoves.first(where: {temp.contains($0 )})
                }
                let random = Int(arc4random_uniform(UInt32(temp.count)))
                if  temp.count > 0{
                    self.collectionView(collectionView, didSelectItemAt: IndexPath(item: bestMove ?? temp[random] as! Int, section: 0))
                }
            }
            
        }
        cell.isUserInteractionEnabled = false
        if  AIMoves.count + playerMoves.count == 9{
            self.showAlert(withString: "Match Drawn!")
        }
    }
    
    func showAlert(withString message:String){
        AudioServicesPlaySystemSound(1519)
        let alert = UIAlertController(title: "Game over", message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "Play again", style: .cancel, handler: { action in
            self.performSegue(withIdentifier: "showSegue", sender: Any?.self)
        }))
        self.present(alert, animated: true)
    }
    
    


    // MARK: - ACTION BUTTONS
    
    @IBAction func pressedStart(_ sender: Any) {
        
    }
    
}
