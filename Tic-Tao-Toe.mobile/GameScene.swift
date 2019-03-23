//
//  GameScene.swift
//  Tic-Tao-Toe.mobile
//
//  Created by Anirudh Kanathala on 3/22/19.
//  Copyright © 2019 kanathala. All rights reserved.
//

import SpriteKit
import GameplayKit

class GameScene: SKScene {
   
    // nodes
    var label = SKLabelNode()
    var outerRectangle = SKShapeNode()
    var gameLabel = SKLabelNode()
    // node collections
    var elements = [SKSpriteNode]()
    var fields: [[SKShapeNode]] = Array(repeating: Array(repeating: SKShapeNode(), count: 3), count: 3)
    
    // helper variables
    var fieldSize: CGSize = CGSize()
    var positions: [[CGPoint]] = Array(repeating: Array(repeating: CGPoint(), count: 3), count: 3)
    
    // game logic
    var board = Board()
    var player = Player()
    var cpu = Player()
    var playerToMove = false
    
    override func didMove(to view: SKView) {
        // initially setup the scene
        setupScene()
    }
    
    func setupScene() {
        // clear any nodes that might be laying around
        self.removeAllChildren()
        
        // game label
        gameLabel = SKLabelNode(text: "Tap to start the game".uppercased())
        gameLabel.position = CGPoint(x: 0, y: 0.275 * self.size.height)
        gameLabel.fontSize = 40
        gameLabel.fontName = "Futura-CondensedMedium"
        gameLabel.fontColor = UIColor.black
        self.addChild(gameLabel)
        
        // label to start game
        label = SKLabelNode(text: "START")
        label.position = CGPoint(x: 0, y: -0.4 * self.size.height)
        label.fontColor = UIColor.white
        label.fontSize = 70
        label.zPosition = 10
        label.fontName = "Futura-CondensedMedium"
        self.addChild(label)
        let labelbg = SKShapeNode(rect: CGRect(x: -0.2 * self.size.width, y: -0.4 * self.size.height - 35, width: 0.4 * self.size.width, height: 120), cornerRadius: 0)
        labelbg.fillColor = UIColor.clear
        labelbg.strokeColor = UIColor.clear
        self.addChild(labelbg)
        
        // outer rectangle
        outerRectangle = SKShapeNode(rect: CGRect(x: -0.45 * self.size.width, y: -0.45 * self.size.width, width: 0.9 * self.size.width, height: 0.9 * self.size.width))
        outerRectangle.fillColor = UIColor.lightGray
        outerRectangle.strokeColor = UIColor.darkGray
        self.addChild(outerRectangle)
        
        // fields
        fieldSize = CGSize(width: CGFloat(0.25) * self.size.width, height: CGFloat(0.25) * self.size.width)
        // nested loop for creating the 9 fields
        for i in 0...2 {
            for j in 0...2 {
                let x = (-0.3 + Double(j) * 0.3) * Double(self.size.width)
                let y = (0.3 - Double(i) * 0.3) * Double(self.size.width)
                positions[i][j] = CGPoint(x: x - 0.5 * Double(fieldSize.width), y: y - 0.5 * Double(fieldSize.height))
                fields[i][j] = SKShapeNode(rect: CGRect(origin: positions[i][j], size: fieldSize))
                positions[i][j] = CGPoint(x: x, y: y)
                fields[i][j].fillColor = UIColor.clear
                fields[i][j].strokeColor = UIColor.clear
                self.outerRectangle.addChild(fields[i][j])
            }
        }
        
        // separation lines
        let lineOffset = 0.025 * Double(self.size.width)
        let lineSize = CGSize(width: 5.0, height: 0.9 * self.size.width - 2.0 * CGFloat(lineOffset))
        // loop for creating all 4 lines
        for i in 0...1 {
            
            // vertical lines
            var x = (-0.15 + 0.3 * Double(i)) * Double(self.size.width)
            var y = (-0.45) * Double(self.size.width)
            var linePoint = CGPoint(x: x - 0.5 * Double(lineSize.width), y: y + lineOffset)
            var lineRect = CGRect(origin: linePoint, size: lineSize)
            var line = SKShapeNode(rect: lineRect, cornerRadius: 5.0)
            line.fillColor = UIColor.black
            line.strokeColor = UIColor.black
            self.outerRectangle.addChild(line)
            
            // horizontal lines
            let flippedLineSize = CGSize(width: lineSize.height, height: lineSize.width)
            x = (-0.45) * Double(self.size.width)
            y = (-0.15 + 0.3 * Double(i)) * Double(self.size.width)
            linePoint = CGPoint(x: x + lineOffset, y: y - 0.5 * Double(flippedLineSize.height))
            lineRect = CGRect(origin: linePoint, size: flippedLineSize)
            line = SKShapeNode(rect: lineRect, cornerRadius: 5.0)
            line.fillColor = UIColor.black
            line.strokeColor = UIColor.black
            self.outerRectangle.addChild(line)
            
        }
    }
    
    // ***********************
    // **** Game behavior ****
    // ***********************
    
    func startGame() {
        // set up game logic
        board = Board()
        player = HumanPlayer()
        cpu = AiPlayer()
        playerToMove = true
    }
    
    func checkGame() {
        // check if game is over. if cpu is to move, then make that move. otherwise, wait for player move.
        if self.board.isOver() {
            gameOver()
        } else if !playerToMove {
            cpuMove()
        }
    }
    
    func gameOver() {
        // send messages according to game result
        let result = self.board.getResult()
        if result == .draw {
            gameLabel.text = "DRAW!"
        } else if result == .opponentWon {
            gameLabel.text = "YOU LOSE!"
        } else if result == .playerWon {
            gameLabel.text = "YOU WIN!"
        } else {
          print("ERRORRRR!")
        }
        playerToMove = false
    }
    
    func cleanupGame() {
        // remove all game sprites
        for e in self.elements {
            e.removeFromParent()
        }
        self.elements.removeAll()
    }
    
    func cpuMove() {
        // execute CPU move
        let (row, col) = self.cpu.getMove(board: self.board)
        self.board.makeMove(row: row, col: col)
        
        let userDefaults = UserDefaults.standard
        if userDefaults.string(forKey: "type") == "circle"{
            self.makeCross(row: row, col: col)
        }else{
            self.makeCircle(row: row, col: col)
        }
        playerToMove = true
        checkGame()
    }
    
    // ***********************
    // *** Game appearance ***
    // ***********************
    
    //creating the mover
    func makeCircle(row: Int, col: Int) {
        let circleTexture = SKTexture(image: #imageLiteral(resourceName: "circle"))
        let circle = SKSpriteNode(texture: circleTexture)
        circle.position = positions[row][col]
        circle.size.width = self.fieldSize.width * 0.8
        circle.size.height = self.fieldSize.height * 0.8
        self.elements.append(circle)
        self.outerRectangle.addChild(circle)
    }
    
    //creating the mover
    func makeCross(row: Int, col: Int) {
        let crossTexture = SKTexture(image: #imageLiteral(resourceName: "cross"))
        let cross = SKSpriteNode(texture: crossTexture)
        cross.position = positions[row][col]
        cross.size.width = self.fieldSize.width * 0.8
        cross.size.height = self.fieldSize.height * 0.8
        self.elements.append(cross)
        self.outerRectangle.addChild(cross)
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches {
            let touchedNodes = nodes(at: t.location(in: self))
            // start node
            if touchedNodes.contains(label) {
                if label.text == "START" {
                    label.text = "CLEAR"
                    self.gameLabel.text = "STARTED"
                    startGame()
                } else if label.text == "CLEAR" {
                    label.text = "START"
                    self.gameLabel.text = "Tap to start the game.".uppercased()
                    cleanupGame()
                }
            }
            // fields in the rectagle box
            for i in 0...2 {
                for j in 0...2 {
                    if touchedNodes.contains(fields[i][j]) {
                        if playerToMove {
                            if self.board.getField(row: i, col: j) == 0 {
                                // if field is empty and player is to move, then make this move
                                self.board.makeMove(row: i, col: j)
                                if UserDefaults.standard.string(forKey: "type") == "cross"{
                                    makeCross(row: i, col: j)
                                }else{
                                    makeCircle(row: i, col: j)
                                }
                                playerToMove = false
                                checkGame()
                            }
                        }
                    }
                }
            }
        }
    }
    
    override func update(_ currentTime: TimeInterval) {
        // Called before each frame is rendered
    }
}
