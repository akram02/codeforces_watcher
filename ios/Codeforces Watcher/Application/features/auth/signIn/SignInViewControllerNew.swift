import SwiftUI
import UIKit

class SignInViewControllerNew: UIHostingController<SignInView> {
    init() {
        super.init(rootView: SignInView())
    }
    
    @objc required dynamic init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        navigationItem.leftBarButtonItem = UIBarButtonItem(
            image: UIImage(named: "back_arrow"),
            style: .plain,
            target: self,
            action: #selector(crossTapped)
        )
    }

    @objc func crossTapped() {
        dismiss(animated: true)
    }
}
