package com.bogdan.codeforceswatcher.features.news.models

import io.xorum.codeforceswatcher.util.FeedUIModel

sealed class NewsItem {

    class FeedbackItem(feedUIModel: FeedUIModel) : NewsItem() {

        val textPositiveButton = feedUIModel.textPositiveButton
        val textNegativeButton = feedUIModel.textNegativeButton
        val textTitle = feedUIModel.textTitle
        val positiveButtonClick = feedUIModel.positiveButtonClick
        val negativeButtonClick = feedUIModel.negativeButtonClick
        val neutralButtonClick = feedUIModel.neutralButtonClick
    }
}