import SwiftUI

struct ViewControllerContainer: UIViewRepresentable {

    var uiView = UIView()
    var viewController: UIViewController

    func makeUIView(context: Context) -> UIView {
        uiView.addSubview(viewController.view)
        viewController.view.run {
            $0.widthToSuperview()
            $0.heightToSuperview()
        }

        return uiView
    }

    func updateUIView(_ uiView: UIView, context: Context) {
        self.uiView.subviews.last?.removeFromSuperview()
        self.uiView.addSubview(viewController.view)
        viewController.view.run {
            $0.widthToSuperview()
            $0.heightToSuperview()
        }
    }
}
