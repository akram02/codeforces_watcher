import SwiftUI
import common

struct ContestsView: View {
    
    var contests: [ContestView.UIModel] = []
    var filterItems: [ContestFilterView.UIModel] = []
    
    var onContest: (ContestView.UIModel) -> Void = { _ in }
    var onCalendar: (ContestView.UIModel) -> Void = { _ in }
    var onFilter: () -> Void = {}
    let refreshControl = UIRefreshControl()
    
    var body: some View {
        VStack(spacing: 0) {
            ContestsNavigationBar(filterItems: filterItems)
            
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
                    
                    ContestView(contest, onContest, onCalendar)
                        .contentShape(Rectangle())
                        .onTapGesture {
                            self.onContest(contest)
                        }
                }
            }
        }
    }
}

struct ContestsView_Previews: PreviewProvider {
    static var previews: some View {
        ContestsView()
    }
}
