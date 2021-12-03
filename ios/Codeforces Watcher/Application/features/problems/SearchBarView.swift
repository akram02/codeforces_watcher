import SwiftUI

struct SearchBarView: View {
    
    var onFilter: () -> Void = {}
    
    @State private var isShownSearchField = false
    @State private var willShowSearchField = false
    @State private var willHideSearchField = false
    @State private var didShownSearchField = false
    @State private var searchBoxText = ""
    
    private let textFieldAnimationDuration = 0.15
    private let placehoderAnimationDelay = 0.1
    
    var body: some View {
        HStack(spacing: 20) {
            if !isShownSearchField {
                CommonText("Problems".localized)
                    .font(.headerMedium)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            
            ZStack(alignment: .trailing) {
                SearchTextField(
                    text: $searchBoxText,
                    placeholder: didShownSearchField ? "Search for problems...".localized : "",
                    willShow: $willShowSearchField,
                    willHide: $willHideSearchField
                )
                    .frame(maxWidth: isShownSearchField ? .infinity : 30)
                    .frame(height: 30)
                
                Button(action: {
                    withAnimation(.easeIn(duration: textFieldAnimationDuration)) {
                        willShowSearchField.toggle()
                        isShownSearchField.toggle()
                        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                            willShowSearchField.toggle()
                        }
                    }
                    DispatchQueue.main.asyncAfter(
                        deadline: .now() + textFieldAnimationDuration + placehoderAnimationDelay
                    ) {
                        didShownSearchField.toggle()
                    }
                }, label: {
                    Image("searchIcon")
                        .renderingMode(.original)
                })
                    .disabled(isShownSearchField)
            }
            
            if isShownSearchField {
                Button(action: {
                    didShownSearchField.toggle()
                    DispatchQueue.main.asyncAfter(deadline: .now() + placehoderAnimationDelay) {
                        withAnimation(.easeIn(duration: textFieldAnimationDuration)) {
                            willHideSearchField.toggle()
                            isShownSearchField.toggle()
                            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                                willHideSearchField.toggle()
                            }
                        }
                    }
                }, label: {
                    Image("crossIconNew")
                        .renderingMode(.original)
                })
            } else {
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

struct SearchBarView_Previews: PreviewProvider {
    static var previews: some View {
        SearchBarView()
    }
}
