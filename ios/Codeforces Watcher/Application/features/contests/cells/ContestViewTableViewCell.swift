import SwiftUI

struct ContestViewTableViewCell: View {
    
    var name: String = ""
    var date: String = ""
    var logoName: String = ""
    
    var onCalendar: () -> Void = {}
    
    var body: some View {
        VStack {
            HStack(spacing: 8) {
                Image(logoName)
                    .resizable()
                    .frame(width: 36, height: 36)
                    .clipShape(Circle())
                
                VStack(spacing: 4) {
                    CommonText(name)
                        .font(.bodySemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    CommonText(date)
                        .font(.hintRegular)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                .lineLimit(1)
                
                Button(action: {
                    self.onCalendar()
                }, label: {
                    Image("contestsIcon")
                        .frame(width: 18, height: 20)
                })
            }
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 10)
    }
}

struct ContestViewTableViewCell_Previews: PreviewProvider {
    static var previews: some View {
        ContestViewTableViewCell()
    }
}
