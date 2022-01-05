import SwiftUI
import common

struct ContestsView: View {
    
    var contests: [Contest] = []
    
    var onContest: (Contest) -> Void = { _ in }
    var onCalendar: (Contest) -> Void = { _ in }
    var refreshControl = UIRefreshControl()
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                NavigationBar(isFilterIcon: true)
                
                RefreshableScrollView(content: {
                    ContestsList
                }, refreshControl: refreshControl)
                    .background(Palette.white.swiftUIColor)
                    .cornerRadius(30, corners: [.topLeft, .topRight])
            }
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    private var ContestsList: some View {
        ScrollView {
            LazyVStack {
                ForEach(contests, id: \.self) { contest in
                    ContestView(contest)
                        .contentShape(Rectangle())
                        .onTapGesture {
                            self.onContest(contest)
                        }
                }
            }
        }
    }
    
    private func ContestView(_ contest: Contest) -> some View {
        let name = contest.title
        let logoName = Contest.Platform.getImageNameByPlatform(contest.platform)
        let date = Double(contest.startDateInMillis / 1000).secondsToContestDateString()
        
        return HStack(spacing: 8) {
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
                onCalendar(contest)
            }, label: {
                Image("calendarAddIcon")
                    .frame(width: 18, height: 20)
            })
        }
        .padding(.horizontal, 20)
        .padding(.vertical, 10)
    }
}

struct ContestsView_Previews: PreviewProvider {
    static var previews: some View {
        ContestsView()
    }
}
