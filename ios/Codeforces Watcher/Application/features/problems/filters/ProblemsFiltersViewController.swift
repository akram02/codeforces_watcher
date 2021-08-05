import UIKit
import common

class ProblemsFiltersViewController: ClosableViewController, ReKampStoreSubscriber {
    
    private let tableView = UITableView()
    private let tableAdapter = FiltersTableViewAdapter()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupView()
    }
    
    private func setupView() {
        view.backgroundColor = Palette.white
        title = "filters".localized
        
        buildViewTree()
        setConstraints()
        setupTableView()
    }
    
    private func buildViewTree() {
        view.addSubview(tableView)
    }
    
    private func setConstraints() {
        tableView.edgesToSuperview()
    }
    
    private func setupTableView() {
        tableView.run {
            $0.delegate = tableAdapter
            $0.dataSource = tableAdapter
            $0.separatorStyle = .none
            $0.rowHeight = 58
        }

        tableView.registerForReuse(cellType: FilterTableViewCell.self)
    }
    
    func onNewState(state: Any) {
        let state = state as! ProblemsState
        
        tableAdapter.filterItems = state.tags.map { tag in
            FilterTableViewCell.UIModel(
                title: tag,
                image: nil,
                isOn: state.selectedTags.contains(tag),
                onSwitchTap: { isChecked in
                    store.dispatch(action: ProblemsRequests.ChangeTagCheckStatus(tag: tag, isChecked: isChecked))
                }
            )
        }
        tableView.reloadData()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        store.subscribe(subscriber: self) { subscription in
            subscription.skipRepeats { oldState, newState in
                return KotlinBoolean(bool:
                    oldState.problems.tags == newState.problems.tags
                        && oldState.problems.selectedTags == newState.problems.selectedTags
                )
            }.select { state in
                return state.problems
            }
        }
    }

    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        store.unsubscribe(subscriber: self)
    }
}
