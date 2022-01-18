import SwiftUI

struct ContestsNavigationBar: View {
    
    var filterItems: [ContestFilterView.UIModel]
    
    @State private var isFiltersDisplayed = false
    
    var body: some View {
        HStack {
            CommonText((isFiltersDisplayed ? "filters" : "Contests").localized)
                .font(.headerMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .animation(nil)
                .modifier(Shake(animatableData: isFiltersDisplayed ? 1 : 0))
            
            Spacer()
            
            if isFiltersDisplayed {
                RightButton("crossIconNew")
            } else {
                RightButton("filterIcon")
            }
        }
        .frame(maxWidth: .infinity)
        .frame(height: 56, alignment: .center)
        .padding(.horizontal, 20)
        
        if isFiltersDisplayed {
            ContestFiltersView(filterItems: filterItems)
                .padding([.horizontal, .vertical], 20)
        }
    }

    
    private func RightButton(_ imageName: String) -> some View {
        
        let filtersViewAnimationDuration = 0.3
        
        return Button(action: {
            withAnimation(.easeOut(duration: filtersViewAnimationDuration)) {
                withAnimation(.spring(response: filtersViewAnimationDuration, dampingFraction: 0.7)) {
                    isFiltersDisplayed.toggle()
                }
            }
        }, label: {
            Image(imageName)
                .renderingMode(.original)
        })
    }
}

fileprivate struct ContestFiltersView: View {
    
    var filterItems: [ContestFilterView.UIModel]
    
    @State private var geometryHeight: CGFloat = 0
    
    private let filterWidth = 50
    private let minSpacerWidth = 20
    
    var body: some View {
        GeometryReader { geometry in
            let filterItemsRowCount = Int(geometry.size.width) / (filterWidth + minSpacerWidth)
            let spacerWidth = (geometry.size.width - CGFloat(filterItemsRowCount * filterWidth)) / CGFloat(filterItemsRowCount - 1)
            
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
        .frame(height: geometryHeight)
    }
    
    @ViewBuilder
    private func ContestFiltersRowView(
        _ filterItemsRow: [ContestFilterView.UIModel],
        spacerWidth: CGFloat
    ) -> some View {
        HStack {
            ForEach(filterItemsRow, id: \.title) { filterItem in
                ContestFilterView(filterItem)
                
                if filterItem.title != filterItemsRow.last?.title {
                    Spacer()
                        .frame(width: spacerWidth)
                }
            }
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

fileprivate extension Array {
    func chunked(into size: Int) -> [[Element]] {
        return stride(from: 0, to: count, by: size).map {
            Array(self[$0 ..< Swift.min($0 + size, count)])
        }
    }
}

struct ContestsNavigationBar_Previews: PreviewProvider {
    static var previews: some View {
        ContestsNavigationBar(filterItems: [])
    }
}
