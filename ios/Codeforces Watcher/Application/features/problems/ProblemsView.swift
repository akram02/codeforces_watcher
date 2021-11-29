import SwiftUI
import common

struct ProblemsView: View {
    
    var problems: [Problem] = []
    
    var body: some View {
        if #available(iOS 14.0, *) {
            ScrollView {
                LazyVStack {
                    ProblemsForEach()
                }
            }
        } else {
            List {
                ProblemsForEach()
            }
            .onAppear {
                UITableView.appearance().separatorStyle = .none
            }
        }
    }
    
    private func ProblemsForEach() -> some View {
        return ForEach(problems, id: \.id) { problem in
            ProblemViewTableViewCell()
                .listRowInsets(EdgeInsets())
        }
    }
}

struct ProblemsView_Previews: PreviewProvider {
    static var previews: some View {
        ProblemsView(problems: [])
    }
}
