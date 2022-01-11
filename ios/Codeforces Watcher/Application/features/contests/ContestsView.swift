import SwiftUI
import common

struct ContestsView: View {
    
    var contests: [ContestUIModel] = []
    var filterItems: [FilterUIModel] = []
    
    var onContest: (ContestUIModel) -> Void = { _ in }
    var onCalendar: (ContestUIModel) -> Void = { _ in }
    var onFilter: () -> Void = {}
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        VStack(spacing: 0) {
            NavigationBarContests(filterItems: filterItems)
            
            RefreshableScrollView(content: {
                if contests.isEmpty {
                    NoItemsView(
                        imageName: "noItemsContests",
                        text: "Contests are on the way to your device...".localized
                    )
                } else {
                    ContestsList
                }
            }, refreshControl: refreshControl)
                .background(Palette.white.swiftUIColor)
                .cornerRadius(30, corners: [.topLeft, .topRight])
        }
        .background(Palette.accentGrayish.swiftUIColor.edgesIgnoringSafeArea(.top))
    }
    
    @ViewBuilder
    private var ContestsList: some View {
        ScrollView {
            LazyVStack {
                ForEach(contests.indices, id: \.self) { index in
                    let contest = contests[index]
                    
                    if index == 0 || contest.startDateMonth != contests[index - 1].startDateMonth {
                        CommonText(contest.startDateMonth.capitalizingFirstLetter())
                            .font(.midHeaderSemibold2)
                            .foregroundColor(Palette.black.swiftUIColor)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .padding(.top, 15)
                            .padding(.bottom, 5)
                            .padding(.horizontal, 20)
                    }
                    
                    ContestView(contest)
                        .contentShape(Rectangle())
                        .onTapGesture {
                            self.onContest(contest)
                        }
                }
            }
        }
    }
    
    @ViewBuilder
    private func ContestView(_ contest: ContestUIModel) -> some View {
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
    
    struct ContestUIModel {
        let startDateMonth: String
        let title: String
        let platformTitle: String
        let platformLogoTitle: String
        let link: String
        let startDateInMillis: Int64
        let durationInMillis: Int64
        let date: String
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
