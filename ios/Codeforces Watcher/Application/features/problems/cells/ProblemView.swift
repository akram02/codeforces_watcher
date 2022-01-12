import SwiftUI
import common

struct ProblemView: View {
    
    var problem: Problem
    
    init(_ problem: Problem) {
        self.problem = problem
    }
    
    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 0) {
                CommonText(problem.title)
                    .font(.bodySemibold)
                    .foregroundColor(Palette.black.swiftUIColor)
                
                CommonText(problem.subtitle)
                    .font(.hintRegular)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
            }
            .lineLimit(1)
            .frame(maxWidth: .infinity, alignment: .leading)
            
            Button(action: {
                onStar()
            }, label: {
                Image("starIconNew")
                    .resizable()
                    .renderingMode(.template)
                    .frame(width: 20, height: 20)
                    .foregroundColor(problem.isFavourite ?
                                        Palette.colorAccent.swiftUIColor : Palette.black.swiftUIColor)
            })
        }
        .padding([.horizontal, .top], 20)
    }
    
    private func onStar() {
        store.dispatch(action: ProblemsRequests.ChangeStatusFavourite(problem: problem))
    }
}
