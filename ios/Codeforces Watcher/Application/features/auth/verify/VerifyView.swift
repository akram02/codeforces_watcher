import SwiftUI

struct VerifyView: View {
    
    @State var codeforcesHandle = ""
    
    var body: some View {
        ScrollView(showsIndicators: false) {
            Spacer()
                .frame(height: 76)
            
            Text("Verify Codeforces Account")
                .font(.bigHeader)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Spacer()
                .frame(height: 44)
            
            VStack(spacing: 0) {
                TextInputLayoutView(
                    text: $codeforcesHandle,
                    hint: "Codeforces handle",
                    placeholder: "Codeforces handle",
                    contentType: .text,
                    tag: 0
                )
                
                Spacer()
                    .frame(height: 24)
                
                VStack(spacing: 0) {
                    Group {
                        Text("To verify that account belongs to you, please, change your English ”Last name” in your")
                            .foregroundColor(Palette.darkGray.swiftUIColor) +
                            
                        Text(" Profile -> Settings -> Social ")
                            .fontWeight(.semibold) +
                            
                        Text("to this value:")
                            .foregroundColor(Palette.darkGray.swiftUIColor)
                    }
                    .font(.primary2)
                    .frame(maxWidth: .infinity, alignment: .leading )
                    
                    Spacer()
                        .frame(height: 12)
                    
                    Text("xV123GH5")
                        .font(.midHeader)
                    
                    Spacer()
                        .frame(height: 20)
                    
                    Text("After successful login you can change it back.")
                        .font(.primary2)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .multilineTextAlignment(.center)
                }
            }
            
            Spacer()
                .frame(height: 72)
            
            Button(action: {}, label: {
                ButtonTextDefault(text: "Verify".uppercased())
            })
        }
        .padding([.horizontal, .bottom])
    }
}

struct VerifyView_Previews: PreviewProvider {
    static var previews: some View {
        VerifyView()
    }
}
