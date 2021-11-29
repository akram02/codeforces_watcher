import SwiftUI

struct FabButtonView: View {
    
    var name: String = ""
    
    var body: some View {
        Button(action: {}, label: {
            Image(name)
                .renderingMode(.original)
        })
    }
}

struct FabButtonView_Previews: PreviewProvider {
    static var previews: some View {
        FabButtonView(name: "infinityIcon")
    }
}
