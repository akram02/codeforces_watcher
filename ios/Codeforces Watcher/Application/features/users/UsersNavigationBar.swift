import SwiftUI

struct UsersNavigationBar: View {
    
    var title: String
    
    var pickerOptions: [String] = []
    var onOptionSelected: (_ option: Int32) -> Void = { _ in }
    
    var body: some View {
        HStack {
            LeftButtonView(title: title)
            Spacer()
            RightButtonView(
                pickerOptions: pickerOptions,
                onOptionSelected: onOptionSelected
            )
        }
        .frame(height: 56, alignment: .center)
        .padding(.horizontal, 20)
    }
}

fileprivate struct LeftButtonView: View {
    
    var title: String
    
    @State private var isLogoDisplayed = true
    
    var body: some View {
        if isLogoDisplayed {
            Image("logo")
                .renderingMode(.original)
                .frame(width: 32, height: 32)
                .transition(.scale.animation(.easeIn(duration: 0.4)))
                .onAppear {
                    DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                        withAnimation {
                            isLogoDisplayed.toggle()
                        }
                    }
                }
        } else {
            CommonText("Users".localized)
                .font(.headerMedium)
                .foregroundColor(Palette.black.swiftUIColor)
                .transition(.opacity.animation(.easeOut(duration: 0.2).delay(0.4)))
        }
    }
}

fileprivate struct RightButtonView: View {
    
    var pickerOptions: [String] = []
    var onOptionSelected: (_ option: Int32) -> Void = { _ in }
    
    @State private var selectedOption: String
    
    init(
        pickerOptions: [String],
        onOptionSelected: @escaping (Int32) -> Void
    ) {
        self.pickerOptions = pickerOptions
        self.onOptionSelected = onOptionSelected
        
        self.selectedOption = pickerOptions.last ?? ""
    }
    
    var body: some View {
        Menu(content: {
            Picker(selection: $selectedOption, content: {
                ForEach(pickerOptions, id: \.self) {
                    Text($0)
                }
            }, label: {
                LabelView
            }).onChange(of: selectedOption) { newValue in
                onOptionSelected(Int32(pickerOptions.firstIndex(of: newValue) ?? 0))
            }
        }, label: {
            LabelView
        })
    }
    
    @ViewBuilder
    private var LabelView: some View {
        Image("filterIcon")
            .renderingMode(.original)
    }
}

struct UsersNavigationBar_Previews: PreviewProvider {
    static var previews: some View {
        UsersNavigationBar(title: "Users")
    }
}
