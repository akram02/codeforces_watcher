import SwiftUI

struct ContestFilterView: View {
    
    var filter: UIModel
    
    init(_ filter: UIModel) {
        self.filter = filter
    }
    
    var body: some View {
        Button(action: {
            filter.onFilter(!filter.isSelected)
        }, label: {
            if filter.isSelected {
                FilterImageView(filter.image)
            } else {
                FilterImageView(filter.image)
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
    
    struct UIModel {
        let title: String
        let image: Image
        let isSelected: Bool
        let onFilter: (_ isSelected: Bool) -> Void
    }
}

struct ContestFilterView_Previews: PreviewProvider {
    static var previews: some View {
        ContestFilterView(
            .init(
                title: "Codeforces",
                image: Image("Codeforces"),
                isSelected: true,
                onFilter: { _ in }
            )
        )
    }
}
