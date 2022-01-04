import SwiftUI

struct ProblemFiltersView: View {
    
    var body: some View {
        ScrollView {
            LazyVStack {
                ForEach(1..<10) { _ in
                    FiltersView()
                }
            }
        }
    }
    
    private func FiltersView() -> some View {
        return HStack {
            CommonText("dfs and similar")
                .font(.bodyRegular)
                .foregroundColor(Palette.black.swiftUIColor)
            
            Spacer()

            Button(action: {}, label: {
                Image("ic_checkbox_unchecked")
            })
        }
        .padding([.horizontal, .top], 20)
    }
}

struct ProblemFiltersView_Previews: PreviewProvider {
    static var previews: some View {
        ProblemFiltersView()
    }
}
