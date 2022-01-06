import SwiftUI

struct NavigationBarContests: View {
    
    var filterItems: [ContestsView.FilterUIModel]
    
    @State var isContestFiltersView = false
    
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
        VStack(alignment: .leading, spacing: 20) {
            let chunkedFilterItems = filterItems.chunked(into: 5)
            ForEach(chunkedFilterItems.indices) { filterItemsRowIndex in
                let filterItemsRow = chunkedFilterItems[filterItemsRowIndex]
                GeometryReader { geometry in
                    HStack {
                        ForEach(filterItemsRow, id: \.title) { filterItem in
                            FilterView(filterItem)
                            
                            if filterItem.title != filterItemsRow.last?.title {
                                Spacer()
                            }
                        }
                    }
                }
                .frame(height: 50)
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
