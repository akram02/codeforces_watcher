package com.bogdan.codeforceswatcher.features.news.models

import io.xorum.codeforceswatcher.features.news.models.News
import io.xorum.codeforceswatcher.util.FeedUIModel

sealed class NewsItem {

    class PostWithCommentItem(post: News.Post, comment: News.Comment) : NewsItem() {

        val blogTitle = post.title

        val postAuthorHandle = post.author.handle
        val postAuthorAvatar = post.author.avatar
        val postAuthorRank = post.author.rank
        val postContent = post.content
        val postLink = post.link
        val postModifiedAt = post.modifiedAt
        val postIsModified = post.isModified

        val commentatorHandle = comment.author.handle
        val commentatorAvatar = comment.author.avatar
        val commentatorRank = comment.author.rank
        val commentContent = comment.content
        val commentLink = comment.link
        val commentCreatedAt = comment.createdAt
    }

    class PostItem(post: News.Post) : NewsItem() {

        val blogTitle = post.title
        val authorHandle = post.author.handle
        val authorAvatar = post.author.avatar
        val authorRank = post.author.rank
        val link = post.link
        val content = post.content
        val modifiedAt = post.modifiedAt
        val isModified = post.isModified
    }

    class PinnedItem(pinnedPost: News.PinnedPost) : NewsItem() {

        val title = pinnedPost.title
        val link = pinnedPost.link
    }

    class FeedbackItem(feedUIModel: FeedUIModel) : NewsItem() {

        val textPositiveButton = feedUIModel.textPositiveButton
        val textNegativeButton = feedUIModel.textNegativeButton
        val textTitle = feedUIModel.textTitle
        val positiveButtonClick = feedUIModel.positiveButtonClick
        val negativeButtonClick = feedUIModel.negativeButtonClick
        val neutralButtonClick = feedUIModel.neutralButtonClick
    }

    class VideoItem(video: News.Video) : NewsItem() {

        val authorHandle = video.author.handle
        val authorAvatar = video.author.avatar
        val authorRank = video.author.rank
        val title = video.title
        val thumbnailLink = video.thumbnailLink
        val link = video.link
        val createdAt = video.createdAt
    }

    object Stub : NewsItem()
}