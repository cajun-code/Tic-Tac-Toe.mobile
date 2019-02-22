//
//  GameViewControllerCoordinator.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit

protocol GameViewControllerCoordinatorDelegate: class {
    func gameViewControllerCoordinatorStopped(_ gameViewControllerCoordinator: GameViewControllerCoordinator)
}

class GameViewControllerCoordinator: NSObject, Coordinator {
    weak var delegate: GameViewControllerCoordinatorDelegate?
    var rootViewController: UIViewController
    var displayedViewController: UIViewController?
    var childCoordinators: [Coordinator] = []
    var selectedIcon: PlayerIcon = .oIcon
    
    @objc public var storyboard: UIStoryboard {
        return UIStoryboard(name: "GameViewController", bundle: Bundle.main)
    }
    
    required init(rootViewController: UIViewController) {
        self.rootViewController = rootViewController
    }
    
    //starting view controller coordinator
    func start() {
        guard let gameViewController = storyboard.instantiateViewController(withIdentifier: "GameViewController") as? GameViewController else {
            preconditionFailure("could not instantiate view controller")
        }
        
        gameViewController.delegate = self
        gameViewController.userIcon = selectedIcon
        
        DispatchQueue.main.async {
            self.present(view: gameViewController, animated: true)
        }
        
        displayedViewController = gameViewController
    }
    
    //any code required to stop the coordinator
    //removing child coordinators and dismissing VC
    func stop() {
        for coordinator in childCoordinators {
            removeChildCoordinator(coordinator)
        }
        
        DispatchQueue.main.async {
            if let displayedVC = self.displayedViewController {
                self.rootViewControllerDismiss(animated: true, intendedVCToPop: displayedVC)
                self.displayedViewController = nil
            }
        }
    }
}

extension GameViewControllerCoordinator: GameViewControllerDelegate {
    func gameCompleted() {
        delegate?.gameViewControllerCoordinatorStopped(self)
    }
}

