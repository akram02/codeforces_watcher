//
//  ProblemTableViewCell.swift
//  Codeforces Watcher
//
//  Created by Den Matyash on 1/17/20.
//  Copyright © 2020 xorum.io. All rights reserved.
//

import UIKit
import common

class ProblemTableViewCell: UITableViewCell {
    
    private let cardView = CardView()

<<<<<<< HEAD
    private let nameLabel = UILabel().apply {
        $0.font = Font.textHeading
        $0.textColor = Palette.black
    }
=======
    private let nameLabel = HeadingLabel()
>>>>>>> #116. Implement pinned post.

    private let contestLabel = UILabel().apply {
        $0.font = Font.textSubheadingBig
        $0.textColor = Palette.grey
    }

    private var problem: Problem!

    private let starIcon = UIImageView(image: UIImage(named: "starIcon")?.withRenderingMode(.alwaysTemplate)).apply {
        $0.tintColor = Palette.grey
    }

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        setupView()
    }

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        setupView()
    }

    private func setupView() {
        selectionStyle = .none

        buildViewTree()
        setConstraints()
        setInteractions()
    }

    private func buildViewTree() {
        contentView.addSubview(cardView)

        [nameLabel, contestLabel, starIcon].forEach(cardView.addSubview)
    }

    private func setConstraints() {
        cardView.edgesToSuperview(insets: UIEdgeInsets(top: 8, left: 8, bottom: 0, right: 8))

        starIcon.run {
            $0.trailingToSuperview(offset: 10)
            $0.centerYToSuperview()
        }

        nameLabel.run {
            $0.topToSuperview(offset: 8)
            $0.leadingToSuperview(offset: 8)
            $0.trailingToSuperview(offset: 48)
        }

        contestLabel.run {
            $0.topToBottom(of: nameLabel, offset: 4)
            $0.leadingToSuperview(offset: 8)
            $0.trailingToSuperview(offset: 48)
        }
    }
    
    private func setInteractions() {
        starIcon.run {
            $0.isUserInteractionEnabled = true
            $0.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(starTapped)))
        }
    }
    
    @objc func starTapped() {
        starIcon.tintColor = problem.isFavourite ? Palette.grey : Palette.colorAccent
        store.dispatch(action: ProblemsRequests.ChangeStatusFavourite(problem: problem))
    }

    func bind(_ problem: Problem) {
        nameLabel.text = "\(problem.contestId)\(problem.index): \(problem.name)"
        contestLabel.text = problem.contestName
        self.problem = problem
        
        starIcon.tintColor = problem.isFavourite ? Palette.colorAccent : Palette.grey
    }
}
