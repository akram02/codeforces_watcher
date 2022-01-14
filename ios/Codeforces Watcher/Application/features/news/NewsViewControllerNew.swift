import SwiftUI
import common

class NewsViewControllerNew: UIHostingController<NewsView> {
    
    private lazy var fabButton = FabButtonViewController(
        name: "newsShareIcon",
        action: { self.onFabButton() }
    )
    
    init() {
        super.init(rootView: NewsView())
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        hideNavigationBar()
        setFabButton()
        fabButton.show()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        fabButton.hide()
    }
    
    private func setFabButton() {
        tabBarController?.tabBar.addSubview(fabButton.view)
        fabButton.setView()
        fabButton.setButtonAction(action: { self.onFabButton() })
    }
    
    private func onFabButton() {
        let activityController = UIActivityViewController(activityItems: ["share_cw_message".localized],
                                                          applicationActivities: nil).apply {
            $0.popoverPresentationController?.run {
                $0.sourceView = self.view
                $0.sourceRect = CGRect(x: self.view.bounds.midX, y: self.view.bounds.height, width: 0, height: 0)
            }
        }
        
        present(activityController, animated: true)
        
        analyticsControler.logEvent(eventName: AnalyticsEvents().SHARE_APP, params: [:])
    }
}
