import SwiftUI
import common

struct ProblemsView: View {
    
    var problems: [Problem] = []
    var noProblemsExplanation = ""
    
    var onFilter: () -> Void = {}
    var onProblem: (String, String) -> Void = { _, _ in }
    var refreshControl = UIRefreshControl()
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                SearchBarView(onFilter: { self.onFilter() })
                
                RefreshableScrollView(content: {
                    if problems.isEmpty {
                        NoItemsView(imageName: "noItemsProblems", text: noProblemsExplanation)
                    } else {
                        ScrollView {
                            LazyVStack {
                                ProblemsForEach
                            }
                        }
                    }
                }, refreshControl: refreshControl)
                    .background(Palette.white.swiftUIColor)
                    .cornerRadius(30, corners: [.topLeft, .topRight])
            }
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    @ViewBuilder
    var ProblemsForEach: some View {
        ForEach(problems, id: \.id) { problem in
            ProblemViewTableViewCell(problem)
                .contentShape(Rectangle())
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
