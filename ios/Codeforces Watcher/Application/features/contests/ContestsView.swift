import SwiftUI
import common

struct ContestsView: View {
    
    var contests: [ContestUIModel] = []
    var filterItems: [FilterUIModel] = []
    
    var onContest: (Contest) -> Void = { _ in }
    var onCalendar: (Contest) -> Void = { _ in }
    
    var refreshControl = UIRefreshControl()
    
    var body: some View {
        ZStack {
            VStack(spacing: 0) {
                NavigationBarContests(filterItems: filterItems)
                
                RefreshableScrollView(content: {
                    ContestsList
                }, refreshControl: refreshControl)
                    .background(Palette.white.swiftUIColor)
                    .cornerRadius(30, corners: [.topLeft, .topRight])
            }
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    @ViewBuilder
    private var ContestsList: some View {
        ScrollView {
            LazyVStack {
                ForEach(contests.indices, id: \.self) { index in
                    let contest = contests[index]
                    
                    if index == 0 || contest.month != contests[index - 1].month {
                        CommonText(contest.month.capitalizingFirstLetter())
                            .font(.midHeaderSemibold2)
                            .foregroundColor(Palette.black.swiftUIColor)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.top, 15)
                            .padding(.bottom, 5)
                            .padding(.horizontal, 20)
                    }
                    
                    ContestView(contest.contest)
                        .contentShape(Rectangle())
                        .onTapGesture {
                            self.onContest(contest.contest)
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
    
    struct ContestUIModel {
        let month: String
        let contest: Contest
    }
    
    struct FilterUIModel {
        let title: String
        let image: Image
        let isSelected: Bool
        let onFilter: (_ isSelected: Bool) -> Void
    }
}

struct ContestsView_Previews: PreviewProvider {
    static var previews: some View {
        ContestsView()
    }
}
