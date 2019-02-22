//
//  StartViewControllerCoordinator.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit

protocol StartViewControllerCoordinatorDelegate: class {
    func startViewControllerCoordinatorStopped()
}

class StartViewControllerCoordinator: NSObject, Coordinator {
    weak var delegate: StartViewControllerCoordinatorDelegate?
    var rootViewController: UIViewController
    var displayedViewController: UIViewController?
    var childCoordinators: [Coordinator] = []
    
    @objc public var storyboard: UIStoryboard {
        return UIStoryboard(name: "StartViewController", bundle: Bundle.main)
    }
    
    required init(rootViewController: UIViewController) {
        self.rootViewController = rootViewController
    }
    
    //starting view controller coordinator
    func start() {
        guard let startViewController = storyboard.instantiateViewController(withIdentifier: "StartViewController") as? StartViewController else {
            preconditionFailure("could not instantiate view controller")
        }
        
        startViewController.delegate = self
        
        DispatchQueue.main.async {
            self.present(view: startViewController, animated: true)
        }
        
        displayedViewController = startViewController
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

//MARK - delegate of the view controller
extension StartViewControllerCoordinator: StartViewControllerDelegate {
    func xButtonSelected() {
        //start coordinator
        let gameCoordinator = GameViewControllerCoordinator(rootViewController: displayedViewController ?? rootViewController)
        gameCoordinator.delegate = self
        gameCoordinator.selectedIcon = .xIcon
        gameCoordinator.start()
        addChildCoordinator(gameCoordinator)
    }
    
    func oButtonSelected() {
        //start coordinator
        let gameCoordinator = GameViewControllerCoordinator(rootViewController: displayedViewController ?? rootViewController)
        gameCoordinator.delegate = self
        gameCoordinator.selectedIcon = .oIcon
        gameCoordinator.start()
        addChildCoordinator(gameCoordinator)
    }
    
    func statsButtonSelected() {
        //start coordinator
        let statsCoordinator = StatsViewControllerCoordinator(rootViewController: displayedViewController ?? rootViewController)
        statsCoordinator.delegate = self
        statsCoordinator.start()
        addChildCoordinator(statsCoordinator)
    }
}

extension StartViewControllerCoordinator: GameViewControllerCoordinatorDelegate {
    func gameViewControllerCoordinatorStopped(_ gameViewControllerCoordinator: GameViewControllerCoordinator) {
        removeChildCoordinator(gameViewControllerCoordinator)
    }
}

extension StartViewControllerCoordinator: StatsViewControllerCoordinatorDelegate {
    func statsViewControllerCoordinatorStopped(_ statsViewControllerCoordinator: StatsViewControllerCoordinator) {
        removeChildCoordinator(statsViewControllerCoordinator)
    }
}
