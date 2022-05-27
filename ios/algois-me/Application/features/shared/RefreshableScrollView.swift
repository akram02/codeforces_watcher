import SwiftUI
import TinyConstraints

struct RefreshableScrollView<Content: View>: UIViewControllerRepresentable {
    
    var content: () -> Content
    var refreshControl: UIRefreshControl
    
    init(
        @ViewBuilder content: @escaping () -> Content,
        refreshControl: UIRefreshControl
    ) {
        self.content = content
        self.refreshControl = refreshControl
    }
    
    func makeUIViewController(context: Context) -> UIScrollViewViewController {
        let scrollView = UIScrollViewViewController().apply {
            $0.hostingController.rootView = AnyView(content())
            $0.refreshControl = refreshControl
        }
        
        return scrollView
    }

    func updateUIViewController(_ uiView: UIScrollViewViewController, context: Context) {
        uiView.hostingController.rootView = AnyView(content())
        uiView.scrollView.refreshControl = refreshControl
    }
}

class UIScrollViewViewController: UIViewController {

    lazy var scrollView: UIScrollView = { return UIScrollView() }()
    var hostingController: UIHostingController<AnyView> = UIHostingController(rootView: AnyView(EmptyView()))
    var refreshControl = UIRefreshControl()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        addRefreshControl()
        addSubviews()
    }
    
    private func addRefreshControl() {
        scrollView.refreshControl = refreshControl
    }
    
    private func addSubviews() {
        self.view.addSubview(self.scrollView)
        pinEdges(of: self.scrollView, to: self.view)

        hostingController.willMove(toParent: self)
        scrollView.addSubview(self.hostingController.view)
        pinEdges(of: self.hostingController.view, to: self.scrollView)
        hostingController.didMove(toParent: self)
    }

    private func pinEdges(of viewA: UIView, to viewB: UIView) {
        viewA.translatesAutoresizingMaskIntoConstraints = false
        
        viewA.edgesToSuperview()
        viewA.widthToSuperview()
        viewA.heightToSuperview()
    }
}
