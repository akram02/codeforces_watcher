import SwiftUI

struct ContestsNavigationBar: View {
    
    var filterItems: [ContestFilterView.UIModel]
    
    @State private var isFiltersDisplayed = false
    @State private var geometryHeight: CGFloat = 0
    
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
        .frame(height: isFiltersDisplayed ? geometryHeight : 0)
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
    
    private func RightButton(_ imageName: String) -> some View {
        
        let filtersViewAnimationDuration = 0.3
        
        return Button(action: {
            withAnimation(.easeOut(duration: filtersViewAnimationDuration)) {
                withAnimation(.spring(response: 0.3, dampingFraction: 0.7)) {
                    isFiltersDisplayed.toggle()
                }
            }
        }, label: {
            Image(imageName)
                .renderingMode(.original)
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
