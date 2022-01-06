import SwiftUI

struct NavigationBarContests: View {
    
    var filterItems: [ContestsView.FilterUIModel]
    
    @State private var isContestFiltersView = false
    @State var geometryHeight: CGFloat = 0
    
    var body: some View {
        HStack {
            CommonText("Contests".localized)
                .font(.headerMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .lineLimit(1)
            
            Spacer()
            
            if isContestFiltersView {
                Button(action: {
                    isContestFiltersView.toggle()
                }, label: {
                    Image("crossIconNew")
                        .renderingMode(.original)
                })
            } else {
                Button(action: {
                    isContestFiltersView.toggle()
                }, label: {
                    Image("filterIcon")
                        .renderingMode(.original)
                })
            }
        }
        .frame(maxWidth: .infinity)
        .frame(height: 56, alignment: .center)
        .padding(.horizontal, 20)
        
        if isContestFiltersView {
            ContestFiltersView
                .padding([.horizontal, .vertical], 20)
        }
    }
    
    @ViewBuilder
    private var ContestFiltersView: some View {
        GeometryReader { geometry in
            let filterItemsRowCount = Int(geometry.size.width) / 70
            let spacerWidth = (geometry.size.width - CGFloat(filterItemsRowCount * 50)) / CGFloat(filterItemsRowCount - 1)
            
            VStack(alignment: .leading, spacing: 20) {
                let chunkedFilterItems = filterItems.chunked(into: filterItemsRowCount)
                
                ForEach(chunkedFilterItems.indices) { filterItemsRowIndex in
                    let filterItemsRow = chunkedFilterItems[filterItemsRowIndex]
                    
                    ContestFiltersRowView(filterItemsRow, spacerWidth: spacerWidth)
                }
            }
            .background(GeometryReader { gm -> Color in
                DispatchQueue.main.async {
                    self.geometryHeight = gm.size.height
                }
                return Color.clear
            })
        }
        .frame(height: geometryHeight)
    }
    
    @ViewBuilder
    private func ContestFiltersRowView(
        _ filterItemsRow: [ContestsView.FilterUIModel],
        spacerWidth: CGFloat
    ) -> some View {
        HStack {
            ForEach(filterItemsRow, id: \.title) { filterItem in
                FilterView(filterItem)
                
                if filterItem.title != filterItemsRow.last?.title {
                    Spacer()
                        .frame(width: spacerWidth)
                }
            }
        }
    }
    
    @ViewBuilder
    private func FilterView(_ filterItem: ContestsView.FilterUIModel) -> some View {
        Button(action: {
            filterItem.onFilter(filterItem.isSelected)
        }, label: {
            filterItem.image
                .renderingMode(.original)
                .resizable()
                .frame(width: 50, height: 50)
        })
    }
}

fileprivate extension Array {
    func chunked(into size: Int) -> [[Element]] {
        return stride(from: 0, to: count, by: size).map {
            Array(self[$0 ..< Swift.min($0 + size, count)])
        }
    }
}

struct NavigationBarContests_Previews: PreviewProvider {
    static var previews: some View {
        NavigationBarContests(filterItems: [])
    }
}
