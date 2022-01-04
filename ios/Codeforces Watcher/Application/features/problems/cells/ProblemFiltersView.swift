import SwiftUI
import common

struct ProblemFiltersView: View {
    
    var filterItems: [ProblemFiltersViewControllerNew.UIModel] = []
    
    var body: some View {
        ScrollView {
            LazyVStack {
                ForEach(filterItems, id: \.title) { filterItem in
                    FilterView(filterItem)
                }
            }
        }
    }
    
    @ViewBuilder
    private func FilterView(
        _ filterItem: ProblemFiltersViewControllerNew.UIModel
    ) -> some View {
        HStack {
            CommonText(filterItem.title)
                .font(.bodyRegular)
                .foregroundColor(Palette.black.swiftUIColor)
                .lineLimit(1)
            
            Spacer()

            Button(action: {
                filterItem.onFilter(!filterItem.isSelected)
            }, label: {
                let imageName = filterItem.isSelected ? "ic_checkbox_checked" : "ic_checkbox_unchecked"
                
                Image(imageName)
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
