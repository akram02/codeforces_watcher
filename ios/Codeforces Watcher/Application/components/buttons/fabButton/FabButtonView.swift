import SwiftUI

struct FabButtonView: View {
    
    var name: String? = nil
    
    var action: () -> Void = {}
    
    var body: some View {
        Button(action: {
            self.action()
        }, label: {
            if let name = name {
                Image(name)
                    .renderingMode(.original)
            } else {
                EmptyView()
            }
        })
    }
}

struct FabButtonView_Previews: PreviewProvider {
    static var previews: some View {
        FabButtonView(name: "infinityIcon")
    }
}
