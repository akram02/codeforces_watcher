import SwiftUI
import common

struct ProblemsView: View {
    
    var problems: [Problem] = []
    var noProblemsExplanation = ""
    
    var onFilter: () -> Void = {}
    var onProblem: (String, String) -> Void = { _, _ in }
    var onStar: (Problem) -> Void = { _ in }
    
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                ProblemsSearchBarView(onFilter: { self.onFilter() })
                
                RefreshableScrollView(content: {
                    if problems.isEmpty {
                        NoItemsView(imageName: "noItemsProblems", text: noProblemsExplanation)
                    } else {
                        ProblemsList
                    }
                }, refreshControl: refreshControl)
                    .background(Palette.white.swiftUIColor)
                    .cornerRadius(30, corners: [.topLeft, .topRight])
            }
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    @ViewBuilder
    var ProblemsList: some View {
        ScrollView {
            LazyVStack {
                ForEach(problems, id: \.id) { problem in
                    ProblemView(problem, onProblem, onStar)
                }
            }
        }
    }
}

struct ProblemsView_Previews: PreviewProvider {
    static var previews: some View {
        ProblemsView()
    }
}
