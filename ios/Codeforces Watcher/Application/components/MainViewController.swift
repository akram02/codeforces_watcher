//
//  NavigationView.swift
//  Codeforces Watcher
//
//  Created by Den Matyash on 1/15/20.
//  Copyright © 2020 xorum.io. All rights reserved.
//
import Foundation
import UIKit

class MainViewController: UITabBarController {
    private let controllers = [
        ActionsViewController().apply(title: "Actions", iconNamed: "actionsIcon"),
        ContestsViewController().apply(title: "Contests", iconNamed: "contestsIcon"),
        ProblemsViewController().apply(title: "Problems", iconNamed: "problemsIcon")
    ]
    
    required init() {
        super.init(nibName: nil, bundle: nil)
        setupView()
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    private func setupView() {
        viewControllers = controllers.map { UINavigationController(rootViewController: $0) }
    }
}

fileprivate extension UIViewController {
    
    func apply(title: String, iconNamed: String) -> UIViewController {
        let tabBarItem = UITabBarItem(title: title.localized, image: UIImage(named: iconNamed), selectedImage: nil)
        
        self.title = title.localized
        self.tabBarItem = tabBarItem
        
        return self
    }
}
