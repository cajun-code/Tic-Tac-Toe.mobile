//
//  StatsViewControllerCoordinator.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit

protocol StatsViewControllerCoordinatorDelegate: class {
    func statsViewControllerCoordinatorStopped(_ statsViewControllerCoordinator: StatsViewControllerCoordinator)
}

class StatsViewControllerCoordinator: NSObject, Coordinator {
    weak var delegate: StatsViewControllerCoordinatorDelegate?
    var rootViewController: UIViewController
    var displayedViewController: UIViewController?
    var childCoordinators: [Coordinator] = []
    
    @objc public var storyboard: UIStoryboard {
        return UIStoryboard(name: "StatsViewController", bundle: Bundle.main)
    }
    
    required init(rootViewController: UIViewController) {
        self.rootViewController = rootViewController
    }
    
    //starting view controller coordinator
    func start() {
        guard let statsViewController = storyboard.instantiateViewController(withIdentifier: "StatsViewController") as? StatsViewController else {
            preconditionFailure("could not instantiate view controller")
        }
        
        statsViewController.delegate = self
        
        DispatchQueue.main.async {
            self.present(view: statsViewController, animated: true)
        }
        
        displayedViewController = statsViewController
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

extension StatsViewControllerCoordinator: StatsViewControllerDelegate {
    func finishedViewingStats() {
        delegate?.statsViewControllerCoordinatorStopped(self)
    }
}

