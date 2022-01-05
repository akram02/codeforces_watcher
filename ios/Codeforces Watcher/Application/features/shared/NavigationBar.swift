import SwiftUI

struct NavigationBar: View {
    
    var isFilterIcon: Bool
    var onFilter: () -> Void = {}
    
    var body: some View {
        HStack {
            CommonText("Contests".localized)
                .font(.headerMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .lineLimit(1)
            
            Spacer()
            
            if isFilterIcon {
                Button(action: {
                    onFilter()
                }, label: {
                    Image("filterIcon")
                        .renderingMode(.original)
                })
            }
        }
        .frame(maxWidth: .infinity)
        .frame(height: 56, alignment: .center)
        .padding(.horizontal, 20)
    }
}

struct NavigationBar_Previews: PreviewProvider {
    static var previews: some View {
        NavigationBar(isFilterIcon: false)
    }
}
