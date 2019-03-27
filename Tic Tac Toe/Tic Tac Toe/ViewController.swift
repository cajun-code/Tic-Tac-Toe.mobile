//
//  ViewController.swift
//  Tic Tac Toe
//
//  Created by  Ome Prakash on 03/22/19.
//  Copyright © 2019 Ome Prakash. All rights reserved.
//

import UIKit

class ViewController: UIViewController , UICollectionViewDelegate, UICollectionViewDelegateFlowLayout , UICollectionViewDataSource{
    
    // MARK:- class variables
    // IBoutlets
    @IBOutlet weak var collectionview: UICollectionView!
    @IBOutlet weak var collectionViewHeightConstraint: NSLayoutConstraint!
    
    
    var isSelectRed = false
    var items : Array<Int>!
    var wins : [[Int]]!
    let edges = [4,0,2,6,8]
    var computerMoves : Array<Int>!
    var userMoves : Array<Int>!
    var userImage : String = "zero"
    var computerImage : String = "cross"
    
    // MARK:- View lifecycle methods
    
    override func viewDidLoad() {
        super.viewDidLoad()
        initialize()
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.collectionview.backgroundColor = .black
        let interitemSpacesCount = numberOfItemPerRow - 1
        let interitemSpacingPerRow = minimumInteritemSpacing * CGFloat(interitemSpacesCount)
        collectionViewHeightConstraint.constant = (itemSize.height * 3) + interitemSpacingPerRow
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        showChoiceAlert()
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // MARK:- Collection View datasource methods
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.items.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "Cell", for: indexPath as IndexPath) as! TicCollectionViewCell
        cell.imgView.image=nil
        
        cell.isUserInteractionEnabled = true
        cell.backgroundColor = .white
        return cell;
    }
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let cell = collectionView.cellForItem(at: indexPath) as! TicCollectionViewCell
        if isSelectRed {
            cell.imgView.image = UIImage(named:self.computerImage)
            isSelectRed = false
            computerMoves.append(indexPath.row)
            if computerMoves.count > 2 {
                if self.isWin(Arr: computerMoves)  {
                    self.showAlert(title: "Computer Wins")
                    
                }
            }
        }
        else
        {
            cell.imgView.image = UIImage(named:self.userImage)
            isSelectRed = true
            userMoves.append(indexPath.row)
            var isBlackWin = false
            if userMoves.count > 2 {
                if  self.isWin(Arr: userMoves) {
                    self.showAlert(title: "You Win")
                    isBlackWin = true
                }
            }
            
            if !isBlackWin {
                let tmp = NSMutableArray()
                let tmpRedBlack = NSMutableArray(array: Array(computerMoves) + Array(userMoves))
                tmp.addObjects(from: items)
                for i in 0 ..< items.count
                {
                    if tmpRedBlack.contains(items[i]) {
                        tmp.remove(items[i])
                    }
                }
                // this logic computes the best value possible
                var bestValue : Int?
                for value in userMoves.filter({$0 != indexPath.row}){
                    for winsArray in wins{
                        if winsArray.contains(value) && winsArray.contains(indexPath.row){
                            for potentialMove in winsArray.filter({tmp.contains($0)}){
                                bestValue = potentialMove
                                break
                            }
                        }
                    }
                    if (bestValue != nil){
                        break
                    }
                }
                if (bestValue == nil){
                    bestValue = edges.first(where: {tmp.contains($0 as! Int)}) as? Int
                }
                let random = Int(arc4random_uniform(UInt32(tmp.count)))
                if  tmp.count > 0{
                    self.collectionView(self.collectionview!, didSelectItemAt: IndexPath(item: bestValue ?? tmp[random] as! Int, section: 0))
                }
            }
        }
        cell.isUserInteractionEnabled = false
        if  computerMoves.count + userMoves.count == 9{
            self.showAlert(title: "Game Over")
        }
        
    }
    
    // MARK:- Helper methods
    
    func initialize() {
        wins = [[0,1,2],[0,3,6],[3,4,5],[1,4,7],[6,7,8],[2,5,8],[0,4,8],[2,4,6]]
        items = [0,1,2,3,4,5,6,7,8]
        definesPresentationContext = true
        self.navigationItem.title = "Tic Tac Toe"
    }
    
    func showChoiceAlert()  {
        let alert = UIAlertController(title: "Select Circle or Cross" as String, message: "", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "Circle", style: .default) {[weak self] action in
            self?.dismiss(animated: true, completion: nil)
            self?.userImage = "zero"
            self?.computerImage = "cross"
            self?.reset()
        })
        alert.addAction(UIAlertAction(title: "Cross", style: .default) {[weak self] action in
            self?.dismiss(animated: true, completion: nil)
            self?.computerImage = "zero"
            self?.userImage = "cross"
            self?.reset()
        })
        self.present(alert, animated: true)
    }
    
    // Resets the game to start again
    func reset() {
        self.collectionview?.reloadData()
        self.isSelectRed = false
        self.computerMoves = []
        self.userMoves = []
    }
    
    // Computes if passed array matches the winning criteria
    func isWin(Arr : Array<Int>) -> Bool {
        var count = Int()
        var isWin = false
        for i in 0 ..< wins.count
        {
            count = 0
            for j in 0 ..< wins[i].count
            {
                if Arr.contains((wins[i])[j])
                {
                    count += 1
                }
                
                if count == 3 {
                    isWin = true
                    break
                }
            }
            if count == 3 {
                break
            }
        }
        return isWin
    }
    
    // Displays common alert
    func showAlert(title : NSString) {
        let alert = UIAlertController(title: title as String, message: "", preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK", style: .default) { action in
            self.dismiss(animated: true, completion: nil)
            self.reset()
        })
        self.present(alert, animated: true)
    }
    
    
    // MARK:- Collection View delegate methods and variables
    
    private let numberOfItemPerRow = 3
    
    private let screenWidth = UIScreen.main.bounds.width
    private let sectionInset = UIEdgeInsets(top: 10, left: 20, bottom: 10, right: 20)
    private let minimumInteritemSpacing = CGFloat(10)
    private let minimumLineSpacing = CGFloat(10)
    
    // Calculate the item size based on the configuration above
    private var itemSize: CGSize {
        let interitemSpacesCount = numberOfItemPerRow - 1
        let interitemSpacingPerRow = minimumInteritemSpacing * CGFloat(interitemSpacesCount)
        let rowContentWidth = screenWidth - sectionInset.right - sectionInset.left - interitemSpacingPerRow
        
        let width = rowContentWidth / CGFloat(numberOfItemPerRow)
        let height = width // feel free to change the height to whatever you want
        
        return CGSize(width: width, height: height)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        return itemSize
    }
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return minimumInteritemSpacing
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return minimumLineSpacing
    }
    
}

