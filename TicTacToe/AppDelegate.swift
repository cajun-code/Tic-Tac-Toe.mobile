//
//  AppDelegate.swift
//  TicTacToe
//
//  Created by ismael zavala on 2/19/19.
//  Copyright © 2019 ismael zavala. All rights reserved.
//

import UIKit 

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?
    lazy fileprivate(set) var appCoordinator: AppCoordinator? = {
        guard let nav = self.window?.rootViewController as? UINavigationController else {
            log.error("Initial view controller is NOT UINavigationController!")
            return nil
        }
        return AppCoordinator(rootViewController: nav)
    }()

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        //starting app coordinator so that the coordinator will decide what flow starts first
        appCoordinator?.start()
        window?.makeKeyAndVisible()
        return true
    }
}

