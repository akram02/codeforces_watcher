//
//  UIViewController+Extension.swift
//  Codeforces Watcher
//
//  Created by Den Matyash on 4/17/20.
//  Copyright © 2020 xorum.io. All rights reserved.
//

import UIKit

extension UIViewController {
    
    func presentModal(_ viewController: UIViewController) {
        present(UINavigationController(rootViewController: viewController).apply() {
            $0.modalPresentationStyle = .fullScreen
            $0.modalTransitionStyle = .crossDissolve
        }, animated: true)
    }
    
    func hideNavigationBar() {
        self.navigationController?.navigationBar.isHidden = true
    }
    
    func setNavigationBarBackground() {
        let standardAppearance = UINavigationBar.appearance().standardAppearance.copy().apply {
            $0.backgroundColor = Palette.accentGrayish
        }
        let scrollEdgeAppearance = UINavigationBar.appearance().scrollEdgeAppearance?.copy().apply {
            $0.backgroundColor = Palette.accentGrayish
        }
        
        self.navigationController?.navigationBar.run {
            $0.standardAppearance = standardAppearance
            $0.scrollEdgeAppearance = scrollEdgeAppearance
        }
    }
}
