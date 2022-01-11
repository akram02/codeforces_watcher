import SwiftUI

struct ContestsNavigationBar: View {
    
    var filterItems: [ContestsView.FilterUIModel]
    
    private let filtersViewAnimationDuration = 0.3
    @State private var isContestFiltersView = false
    @State private var geometryHeight: CGFloat = 0
    
    var body: some View {
        HStack {
            CommonText((isContestFiltersView ? "filters" : "Contests").localized)
                .font(.headerMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .animation(nil)
                .modifier(Shake(animatableData: isContestFiltersView ? 1 : 0))
            
            Spacer()
            
            if isContestFiltersView {
                Button(action: {
                    withAnimation(.easeOut(duration: filtersViewAnimationDuration)) {
                        withAnimation(.spring(response: 0.3, dampingFraction: 0.7)) {
                            isContestFiltersView.toggle()
                        }
                    }
                }, label: {
                    Image("crossIconNew")
                        .renderingMode(.original)
                })
            } else {
                Button(action: {
                    withAnimation(.easeOut(duration: filtersViewAnimationDuration)) {
                        withAnimation(.spring(response: 0.3, dampingFraction: 0.7)) {
                            isContestFiltersView.toggle()
                        }
                    }
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
            .background(GeometryReader { g -> Color in
                DispatchQueue.main.async {
                    self.geometryHeight = g.size.height
                }
                return Color.clear
            })
        }
        .frame(height: isContestFiltersView ? geometryHeight : 0)
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
            filterItem.onFilter(!filterItem.isSelected)
        }, label: {
            if filterItem.isSelected {
                FilterImageView(filterItem.image)
            } else {
                FilterImageView(filterItem.image)
                    .saturation(0)
                    .opacity(0.5)
            }
        })
    }
    
    @ViewBuilder
    private func FilterImageView(_ filterImage: Image) -> some View {
        filterImage
            .renderingMode(.original)
            .resizable()
            .frame(width: 50, height: 50)
            .environment(\.colorScheme, .dark)
    }
}

fileprivate extension Array {
    func chunked(into size: Int) -> [[Element]] {
        return stride(from: 0, to: count, by: size).map {
            Array(self[$0 ..< Swift.min($0 + size, count)])
        }
    }
}

fileprivate struct Shake: GeometryEffect {
    var animatableData: CGFloat

    func effectValue(size: CGSize) -> ProjectionTransform {
        ProjectionTransform(CGAffineTransform(
            translationX: sin(animatableData * .pi),
            y: 0
        ))
    }
}

struct ContestsNavigationBar_Previews: PreviewProvider {
    static var previews: some View {
        ContestsNavigationBar(filterItems: [])
    }
}
