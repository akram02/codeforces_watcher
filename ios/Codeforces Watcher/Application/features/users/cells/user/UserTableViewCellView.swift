import SwiftUI

struct UserTableViewCellView: View {
    
    var userImage = Image("noImage")
    var userHandle = "".attributed
    var userRating = "".attributed
    var dateOfLastRatingUpdate: String = ""
    var valueOfLastRatingUpdate = "".attributed
    
    var body: some View {
        HStack(spacing: 0) {
            userImage
            
            Spacer()
                .frame(width: 8)
            
            VStack(spacing: 4) {
                HStack {
                    Text(userHandle.string)
                        .font(.primarySemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                    
                    Spacer()
                    
                    Text(userRating.string)
                        .font(.primarySemibold)
                        .foregroundColor(Palette.black.swiftUIColor)
                }
                
                HStack {
                    Text(dateOfLastRatingUpdate)
                        .font(.hintRegular)
                        .foregroundColor(Palette.darkGray.swiftUIColor)
                        .lineLimit(1)
                    
                    Spacer()
                    
                    Text(valueOfLastRatingUpdate.string)
                }
            }
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 10)
    }
}

struct UserTableViewCellView_Previews: PreviewProvider {
    static var previews: some View {
        UserTableViewCellView()
    }
}
