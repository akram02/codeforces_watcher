import SwiftUI
import common

class ProblemFiltersViewController: UIHostingController<ProblemFiltersView>, ReKampStoreSubscriber {
    
    init() {
        super.init(rootView: ProblemFiltersView())
        
        setNavigationBar()
    }
    
    @objc required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                KotlinBoolean(bool:
                    oldState.problems.tags == newState.problems.tags
                        && oldState.problems.selectedTags == newState.problems.selectedTags
                )
            }.select { state in
                state.problems
            }
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
        store.unsubscribe(subscriber: self)
    }
    
    private func setNavigationBar() {
        setNavigationBarBackground()
        
        navigationItem.leftBarButtonItems = [
            UIBarButtonItem(
                image: UIImage(named: "back_arrow"),
                style: .plain,
                target: self,
                action: #selector(closeViewController)
            ),
            UIBarButtonItem(title: "Filters").apply {
                $0.isEnabled = false
                $0.setTitleTextAttributes(
                    [
                        NSAttributedString.Key.font: Font.monospacedHeaderMedium,
                        NSAttributedString.Key.foregroundColor: Palette.black,
                        NSAttributedString.Key.kern: -1
                    ],
                    for: .disabled)
            }
        ]
    }
    
    func onNewState(state: Any) {
        let state = state as! ProblemsState
        
        let filterItems = state.tags.map { tag in
            ProblemFiltersView.UIModel(
                title: tag,
                isSelected: state.selectedTags.contains(tag),
                onFilter: { isChecked in
                    store.dispatch(action: ProblemsRequests.ChangeTagCheckStatus(tag: tag, isChecked: isChecked))
                }
            )
        }
        
        rootView.filterItems = filterItems
    }
    
    @objc func closeViewController() {
        dismiss(animated: true)
    }
}
