//
//  AppCoordinator.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import UIKit

class AppCoordinator: Coordinator {
    var rootViewController: UIViewController
    var childCoordinators = [Coordinator]()
    
    /// The current view controller that is being displayed
    public var displayedViewController: UIViewController?
    
    required init(rootViewController: UIViewController) {
        self.rootViewController = rootViewController
    }
    
    //method to begin the first flow
    func start() {
        guard let root = rootController else {
            log.error("unable to get root VC")
            return
        }
        beginStartViewControllerFlow(root)
    }
    
    func stop() {
        //any code needed to stop coordinator
    }
    
    //initializing first Coordinator
    func beginStartViewControllerFlow(_ rootVC: RootViewController) {
        let vcCoordinator = StartViewControllerCoordinator.init(rootViewController: rootVC)
        vcCoordinator.delegate = self
        vcCoordinator.start()
        addChildCoordinator(vcCoordinator)
    }
}

// MARK: - computed Properties
extension AppCoordinator {
    var navController: UINavigationController? {
        return rootViewController as? UINavigationController
    }
    fileprivate var rootController: RootViewController? {
        return navController?.viewControllers.first as? RootViewController
    }
}

// MARK: - RootViewControllerDelegate
//delegate function from root to begin the flow again
//could be used in the future to determine what the new root should be
//ex: a login flow vs the main app flow
extension AppCoordinator: RootViewControllerDelegate {
    func presentHome(_ rootVC: RootViewController) {
        beginStartViewControllerFlow(rootVC)
    }
}

//extension for coordinator delegate
extension AppCoordinator: StartViewControllerCoordinatorDelegate {
    func startViewControllerCoordinatorStopped() {
        //expansion for the future for possible flows
        //ex main app vs login flow
    }
}
