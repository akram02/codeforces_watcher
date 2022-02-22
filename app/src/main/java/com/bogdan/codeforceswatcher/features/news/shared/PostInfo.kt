package com.bogdan.codeforceswatcher.features.news.shared

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.CwApp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.users.UserAvatar
import com.bogdan.codeforceswatcher.features.users.colorTextByUserRankNew
import com.bogdan.codeforceswatcher.features.users.getColorByUserRank
import io.xorum.codeforceswatcher.features.news.models.News
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

@Composable
fun PostInfo(
    author: News.User,
    title: String,
    modifiedAt: Long,
    isModified: Boolean
) = Row(
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    UserAvatar(
        avatar = author.avatar,
        modifier = Modifier.border(1.dp, colorResource(getColorByUserRank(author.rank)), CircleShape)
    )

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            style = AlgoismeTheme.typography.primarySemiBold,
            color = AlgoismeTheme.colors.secondary,
            maxLines = 1
        )

        Text(
            text = buildPostAgoText(author, modifiedAt, isModified),
            style = AlgoismeTheme.typography.hintRegular,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.offset(y = (-2).dp)
        )
    }
}

@Composable
private fun buildPostAgoText(user: News.User, time: Long, isModified: Boolean) = buildAnnotatedString {
    val handle = colorTextByUserRankNew(user.handle, user.rank)
    val postState = CwApp.app.getString(if (isModified) R.string.modified else R.string.created)

    return handle + AnnotatedString(" • $postState ${PrettyTime().format(Date(time * 1000))}")
}