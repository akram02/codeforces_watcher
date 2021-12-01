import SwiftUI
import common

struct ProblemsView: View {
    
    var problems: [Problem] = []
    
    var onFilter: () -> Void = {}
    var onProblem: (String, String) -> Void = { _, _ in }
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                searchBar
                
                if #available(iOS 14.0, *) {
                    IOS14View
                } else {
                    IOS13View
                }
            }
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    var searchBar: some View {
        HStack(spacing: 20) {
            CommonText("Problems".localized)
                .font(.headerMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Image("searchIcon")
            
            Button(action: {
                self.onFilter()
            }, label: {
                Image("filterIcon")
            })
        }
        .frame(maxWidth: .infinity)
        .frame(height: 56, alignment: .center)
        .padding(.horizontal, 20)
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
