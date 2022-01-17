//
//  NewsItem.swift
//  Codeforces Watcher
//
//  Created by Den Matyash on 06.10.2020.
//  Copyright © 2020 xorum.io. All rights reserved.
//

import Foundation
import common
import UIKit

enum NewsItem {
    
    struct PostWithCommentItem {
        let blogTitle: String
        let postAuthorAvatar: String
        let postAuthorHandle: NSAttributedString
        let postContent: String
        let postLink: String
        let postAuthorRankColor: CGColor
        let postAgoText: String

        let commentatorAvatar: String
        let commentatorHandle: NSAttributedString
        let commentContent: String
        let commentLink: String
        let commentatorRankColor: CGColor
        let commentAgoText: String

        init(_ comment: News.Comment, _ post: News.Post) {
            blogTitle = post.title

            postAuthorAvatar = post.author.avatar
            postAuthorHandle = colorTextByUserRank(text: post.author.handle, rank: post.author.rank)
            postContent = post.content
            postLink = post.link
            postAuthorRankColor = getColorByUserRank(post.author.rank).cgColor

            commentatorAvatar = comment.author.avatar
            commentatorHandle = colorTextByUserRank(text: comment.author.handle, rank: comment.author.rank)
            commentContent = comment.content
            commentLink = comment.link
            commentatorRankColor = getColorByUserRank(comment.author.rank).cgColor
            
            postAgoText = post.modifiedAt.buildPostAgoText(post.isModified)
            commentAgoText = comment.createdAt.buildAgoText()
        }
    }
    
    struct PostItem {
        let authorHandle: NSAttributedString
        let blogTitle: String
        let authorAvatar: String
        let link: String
        let rankColor: CGColor
        let content: String
        let agoText: String
        
        init(_ post: News.Post) {
            authorHandle = colorTextByUserRank(text: post.author.handle, rank: post.author.rank)
            blogTitle = post.title
            authorAvatar = post.author.avatar
            link = post.link
            rankColor = getColorByUserRank(post.author.rank).cgColor
            content = post.content
            agoText = post.modifiedAt.buildPostAgoText(post.isModified)
        }
    }
    
    struct PinnedItem {
        let link: String
        let title: String
        
        init(_ pinnedPost: News.PinnedPost) {
            link = pinnedPost.link
            title = pinnedPost.title
        }
    }
    
    struct FeedbackItem {
        let textPositiveButton: String
        let textNegativeButton: String
        let textTitle: String
        let positiveButtonClick: () -> ()
        let negativeButtonClick: () -> ()
        let neutralButtonClick: () -> ()

        init(_ feedUiModel: FeedUIModel) {
            textPositiveButton = feedUiModel.textPositiveButton
            textNegativeButton = feedUiModel.textNegativeButton
            textTitle = feedUiModel.textTitle
            positiveButtonClick = feedUiModel.positiveButtonClick
            negativeButtonClick = feedUiModel.negativeButtonClick
            neutralButtonClick = feedUiModel.neutralButtonClick
        }
    }
    
    struct VideoItem {
        let authorHandle: NSAttributedString
        let authorAvatar: String
        let rankColor: CGColor
        let title: String
        let agoText: String
        let thumbnailLink: String
        let link: String
        
        init(_ video: News.Video) {
            authorAvatar = video.author.avatar
            rankColor = getColorByUserRank(video.author.rank).cgColor
            title = video.title
            agoText = video.createdAt.buildAgoText()
            thumbnailLink = video.thumbnailLink
            link = video.link
            authorHandle = colorTextByUserRank(text: video.author.handle, rank: video.author.rank)
        }
    }
    
    case postWithCommentItem(PostWithCommentItem)
    case postItem(PostItem)
    case pinnedItem(PinnedItem)
    case feedbackItem(FeedbackItem)
    case videoItem(VideoItem)
    
//    static var allCases: [PostWithCommentItem, PostItem, PinnedItem, FeedbackItem, VideoItem]
}

fileprivate extension Int64 {
    func buildAgoText() -> String {
        guard let timePassed = TimeInterval((Int(Date().timeIntervalSince1970) - Int(self))).socialDate else { fatalError() }
        return "ago".localizedFormat(args: timePassed)
    }
    
    func buildPostAgoText(_ isModified: Bool) -> String {
        guard let timePassed = TimeInterval((Int(Date().timeIntervalSince1970) - Int(self))).socialDate else { fatalError() }
        let postState = (isModified ? "modified" : "created").localized
        return "post_ago".localizedFormat(args: postState, timePassed)
    }
}
