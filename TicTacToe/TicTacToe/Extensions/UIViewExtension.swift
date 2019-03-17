//
//  UIViewExtension.swift
//  TicTacToe
//
//  Created by Naraharisetty, Venkat on 3/16/19.
//  Copyright © 2019 Naraharisetty, Venkat. All rights reserved.
//

import UIKit
import Foundation
import Lottie

var vSpinner : UIView?

extension UIViewController {
    /**
     * Show Activity Indicator view when computer is thinking.
     */
    func showSpinner(onView : UIView) {
        let spinnerView = UIView.init(frame: onView.bounds)
        spinnerView.backgroundColor = UIColor.init(red: 0.5, green: 0.5, blue: 0.5, alpha: 0.5)
        let ai = UIActivityIndicatorView.init(style: .whiteLarge)
        ai.startAnimating()
        ai.center = spinnerView.center
        
        DispatchQueue.main.async {
            spinnerView.addSubview(ai)
            onView.addSubview(spinnerView)
        }
        
        vSpinner = spinnerView
    }
    
    /**
     * Removing Activity Indicator.
     */
    func removeSpinner() {
        DispatchQueue.main.async {
            vSpinner?.removeFromSuperview()
            vSpinner = nil
        }
    }
    
    /**
     * Custom Activity Indicator using Lottie animation
     */
    func lottieShowSpinner(onView : UIView) {
        let spinnerView = UIView.init(frame: onView.bounds)
        spinnerView.backgroundColor = UIColor.init(red: 0.5, green: 0.5, blue: 0.5, alpha: 0.5)
        let ai = LOTAnimationView()
        //ai.setAnimation(named:"690-loading")
        ai.setAnimation(named:"lottie_animated spinner")
        ai.loopAnimation = true
        ai.play()
        DispatchQueue.main.async {
            spinnerView.addSubview(ai)
            onView.addSubview(spinnerView)
        }
        vSpinner = spinnerView
    }
    /**
     *Remove  Custom Activity Indicator using Lottie animation
     */
    func lottieRemoveSpinner() {
        DispatchQueue.main.async {
            vSpinner?.removeFromSuperview()
            vSpinner = nil
        }
    }
    
}



