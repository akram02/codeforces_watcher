import SwiftUI

struct ProblemsSearchBarView: View {
    
    var onFilter: () -> Void = {}
    
    @State private var isShownSearchField = false
    @State private var willShowSearchField = false
    @State private var willHideSearchField = false
    @State private var didShownSearchField = false
    @State private var searchBoxText = ""
    
    private let textFieldAnimationDuration = 0.15
    private let placehoderAnimationDelay = 0.2
    
    var body: some View {
        HStack(spacing: 20) {
            if !isShownSearchField {
                TitleBar
            }
            
            ZStack(alignment: .trailing) {
                SearchTextFieldView
                
                SearchIconView
            }
            
            if isShownSearchField {
                CrossIconView
            } else {
                FilterIconView
            }
        }
        .frame(maxWidth: .infinity)
        .frame(height: 56, alignment: .center)
        .padding(.horizontal, 20)
    }
    
    @ViewBuilder
    private var TitleBar: some View {
        CommonText("Problems".localized)
            .font(.headerMedium)
            .foregroundColor(Palette.black.swiftUIColor)
            .frame(maxWidth: .infinity, alignment: .leading)
    }
    
    @ViewBuilder
    private var SearchTextFieldView: some View {
        ProblemsSearchTextField(
            text: $searchBoxText,
            placeholder: didShownSearchField ? "Search for problems...".localized : "",
            willShow: $willShowSearchField,
            willHide: $willHideSearchField
        )
            .frame(maxWidth: isShownSearchField ? .infinity : 30)
            .frame(height: 30)
    }
    
    @ViewBuilder
    private var SearchIconView: some View {
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
    
    @ViewBuilder
    private var FilterIconView: some View {
        Button(action: {
            onFilter()
        }, label: {
            Image("filterIcon")
                .renderingMode(.original)
        })
    }
    
    @ViewBuilder
    private var CrossIconView: some View {
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
    }
}

struct ProblemsSearchBarView_Previews: PreviewProvider {
    static var previews: some View {
        ProblemsSearchBarView()
    }
}
