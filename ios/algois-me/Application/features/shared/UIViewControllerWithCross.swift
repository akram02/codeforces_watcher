import Foundation
import UIKit

class ClosableViewController: UIViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            image: UIImage(named: "crossIcon"), 
            style: .plain, 
            target: self, 
            action: #selector(crossTapped)
        )
    }
    
    @objc func crossTapped() {
        dismiss(animated: true)
    }
}
