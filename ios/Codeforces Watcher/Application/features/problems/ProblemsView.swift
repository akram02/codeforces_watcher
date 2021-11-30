import SwiftUI
import common

struct ProblemsView: View {
    
    var problems: [Problem] = []
    
    var onProblem: (String, String) -> Void = { _, _ in }
    
    var body: some View {
        ZStack {
            if #available(iOS 14.0, *) {
                IOS14View
            } else {
                IOS13View
            }
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    var IOS13View: some View {
        List {
            ProblemsForEach
        }
        .onAppear {
            UITableView.appearance().separatorStyle = .none
        }
        .screenBackground()
    }
    
    @available(iOS 14.0, *)
    var IOS14View: some View {
        ScrollView {
            LazyVStack {
                ProblemsForEach
            }
        }
        .screenBackground()
    }
    
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
