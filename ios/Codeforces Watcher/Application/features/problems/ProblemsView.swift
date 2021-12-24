import SwiftUI
import common

struct ProblemsView: View {
    
    var problems: [Problem] = []
    
    var onFilter: () -> Void = {}
    var onProblem: (String, String) -> Void = { _, _ in }
    var refreshControl: UIRefreshControl = UIRefreshControl()
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                SearchBarView(onFilter: { self.onFilter() })
                
                RefreshableScrollView(content: {
                    if #available(iOS 14.0, *) {
                        IOS14View
                    } else {
                        IOS13View
                    }
                },refreshControl: refreshControl)
                    .screenBackground()
            }
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    @ViewBuilder
    var IOS13View: some View {
        List {
            ProblemsForEach
        }
        .onAppear {
            UITableView.appearance().separatorStyle = .none
        }
    }
    
    @ViewBuilder
    @available(iOS 14.0, *)
    var IOS14View: some View {
        ScrollView {
            LazyVStack {
                ProblemsForEach
            }
        }
    }
    
    @ViewBuilder
    var ProblemsForEach: some View {
        ForEach(problems, id: \.id) { problem in
            ProblemViewTableViewCell(problem)
                .listRowInsets(EdgeInsets())
                .onTapGesture {
                    self.onProblem(problem.link, problem.title)
                }
        }
    }
}

struct ProblemsView_Previews: PreviewProvider {
    static var previews: some View {
        ProblemsView(problems: [])
    }
}
