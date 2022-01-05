import SwiftUI
import common

struct ProblemFiltersView: View {
    
    var filterItems: [UIModel] = []
    
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
    private func FilterView(_ filterItem: UIModel) -> some View {
        HStack {
            CommonText(filterItem.title)
                .font(.bodyRegular)
                .foregroundColor(Palette.black.swiftUIColor)
                .lineLimit(1)
            
            Spacer()

            Button(action: {
                filterItem.onFilter(!filterItem.isSelected)
            }, label: {
                Image(filterItem.isSelected ? "ic_checkbox_checked" : "ic_checkbox_unchecked")
            })
        }
        .padding([.horizontal, .top], 20)
    }
    
    struct UIModel {
        let title: String
        let isSelected: Bool
        let onFilter: (_ isOn: Bool) -> ()
    }
}

struct ProblemFiltersView_Previews: PreviewProvider {
    static var previews: some View {
        ProblemFiltersView()
    }
}
