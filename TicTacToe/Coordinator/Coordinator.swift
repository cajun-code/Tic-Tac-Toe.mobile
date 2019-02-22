//
//  Coordinator.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/21/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import Foundation
import UIKit

//protocol all coordinators should follow
protocol Coordinator: class {
    
    //root view controller for the coordinator
    var rootViewController: UIViewController { get }
    
    //current view controller displayed
    var displayedViewController: UIViewController? { get set }
    
    //aray containing any child coordinators
    var childCoordinators: [Coordinator] { get set }
    
    //Present from root view controller
    init(rootViewController: UIViewController)
    
    //tells the coordinator to create its initial view controller and take over the flow
    func start()
    
    /// Tell the coordinator to dismiss any displayed VC and clean up any of its child coordinators
    func stop()
}

// Helper methods
extension Coordinator {
    //adds the VC to the stack
    public func present(view: UIViewController, animated: Bool) {
        if let rootNavigationController = self.rootViewController as? UINavigationController ?? self.rootViewController.navigationController {
            rootNavigationController.pushViewController(view, animated: animated)
            return
        }
        
        let rootNav = UINavigationController(rootViewController: view)
        rootNav.modalPresentationStyle = .fullScreen
        self.rootViewController.present(rootNav, animated: animated, completion: nil)
    }
    
    //func used to remove VC from the stack
    public func rootViewControllerDismiss(animated: Bool, intendedVCToPop: UIViewController) {
        if let rootNavigationController = self.rootViewController as? UINavigationController ?? self.rootViewController.navigationController {
            guard let lastVC = rootNavigationController.children.last else {
                log.error("unable to get the last VC in the nav stack")
                return
            }
            
            guard lastVC == intendedVCToPop else {
                log.error("intended vc to pop doesn't match last VC on stack")
                return
            }
            
            rootNavigationController.popViewController(animated: animated)
            return
        }
        self.rootViewController.dismiss(animated: animated, completion: nil)
    }
    
    /// Add a child coordinator to the parent
    public func addChildCoordinator(_ childCoordinator: Coordinator) {
        self.childCoordinators.append(childCoordinator)
    }
    
    /// Remove a child coordinator from the parent
    public func removeChildCoordinator(_ childCoordinator: Coordinator) {
        childCoordinator.stop()
        self.childCoordinators = self.childCoordinators.filter { $0 !== childCoordinator }
    }
}
