import SwiftUI

struct RestorePasswordMailSentView: View {
    
    var body: some View {
        VStack(spacing: 0) {
            Spacer()
                .frame(height: 76)
            
            VStack(spacing: 12) {
                Text("Check your box.")
                    .font(.subHeader)
                
                Text("Tap to open your mail app")
                    .font(.header)
            }
            
            Spacer()
                .frame(height: 68)
            
            Image("email")
                .resizable()
                .frame(width: 108, height: 80)
                .scaledToFit()
            
            Spacer()
                .frame(height: 68)
            
            VStack(spacing: 20) {
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                    Text("Open mail")
                        .font(.primarySemibold)
                        .foregroundColor(Palette.white.swiftUIColor)
                        .frame(width: 250, height: 40)
                        .background(Palette.black.swiftUIColor)
                        .cornerRadius(30)
                })
                
                Button(action: /*@START_MENU_TOKEN@*/{}/*@END_MENU_TOKEN@*/, label: {
                    Text("Back to sign in")
                        .font(.primarySemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .frame(width: 248, height: 38)
                        .background(Palette.white.swiftUIColor)
                        .overlay(
                            RoundedRectangle(cornerRadius: 30)
                                .stroke(Palette.black.swiftUIColor, lineWidth: 2)
                        )
                })
            }
            
            Spacer()
            Spacer()
            
            Text("If you haven’t received a message within 5 minutes, check your Spam folder.")
                .font(.primary2)
                .foregroundColor(Palette.darkGray.swiftUIColor)
                .multilineTextAlignment(.center)
        }
        .padding()
    }
}

struct RestorePasswordMailSentView_Previews: PreviewProvider {
    static var previews: some View {
        RestorePasswordMailSentView()
    }
}
