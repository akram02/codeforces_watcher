import SwiftUI

struct ContestView: View {
    
    var contest: UIModel
    var onContest: (UIModel) -> Void = { _ in }
    var onCalendar: (UIModel) -> Void = { _ in }
    
    init (
        _ contest: UIModel,
        _ onContest: @escaping (UIModel) -> Void,
        _ onCalendar: @escaping (UIModel) -> Void
    ) {
        self.contest = contest
        self.onContest = onContest
        self.onCalendar = onCalendar
    }
    
    var body: some View {
        HStack(spacing: 8) {
            Image(contest.platformLogoTitle)
                .resizable()
                .frame(width: 36, height: 36)
                .clipShape(Circle())
            
            VStack(spacing: 4) {
                CommonText(contest.title)
                    .font(.bodySemibold)
                    .foregroundColor(Palette.black.swiftUIColor)
                    .frame(maxWidth: .infinity, alignment: .leading)
                
                CommonText(contest.date)
                    .font(.hintRegular)
                    .foregroundColor(Palette.darkGray.swiftUIColor)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .lineLimit(1)
            
            Button(action: {
                onCalendar(contest)
            }, label: {
                Image("calendarAddIcon")
                    .frame(width: 18, height: 20)
            })
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 10)
    }
    
    struct UIModel {
        let startDateMonth: String
        let title: String
        let platformTitle: String
        let platformLogoTitle: String
        let link: String
        let startDateInMillis: Int64
        let durationInMillis: Int64
        let date: String
    }
}

struct ContestView_Previews: PreviewProvider {
    static var previews: some View {
        ContestView(
            .init(
                startDateMonth: "January",
                title: "Hello 2022!",
                platformTitle: "Codeforces",
                platformLogoTitle: "Codeforces",
                link: "https://codeforces.com",
                startDateInMillis: 1,
                durationInMillis: 1,
                date: "01-01-1970"
            ),
            { _ in },
            { _ in }
        )
    }
}
