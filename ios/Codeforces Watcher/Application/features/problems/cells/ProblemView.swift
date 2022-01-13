import SwiftUI
import common

struct ProblemView: View {
    
    var problem: Problem
    var onProblem: (String, String) -> Void = { _, _ in }
    var onStar: (Problem) -> Void = { _ in }
    
    init(
        _ problem: Problem,
        _ onProblem: @escaping (String, String) -> Void,
        _ onStar: @escaping (Problem) -> Void
    ) {
        self.problem = problem
        self.onProblem = onProblem
        self.onStar = onStar
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
                onStar(problem)
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
        .contentShape(Rectangle())
        .onTapGesture {
            onProblem(problem.link, problem.title)
        }
    }
}
