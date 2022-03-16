package com.bogdan.codeforceswatcher.features.news.cells

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.CwApp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.news.shared.PostContentView
import com.bogdan.codeforceswatcher.features.news.shared.SeeAllCommentsView
import com.bogdan.codeforceswatcher.features.users.UserAvatar
import com.bogdan.codeforceswatcher.features.users.colorTextByUserRankNew
import com.bogdan.codeforceswatcher.features.users.getColorByUserRank
import io.xorum.codeforceswatcher.features.news.models.News
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

@Composable
fun PostWithCommentView(
    post: News.Post,
    comment: News.Comment,
    onPost: (String, String) -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
        .padding(top = 20.dp)
        .clip(RoundedCornerShape(20.dp))
        .background(AlgoismeTheme.colors.lightGray),
    verticalArrangement = Arrangement.spacedBy(10.dp)
) {
    PostContentView(
        title = post.title,
        content = post.content,
        handle = post.author.handle,
        avatar = post.author.avatar,
        rank = post.author.rank,
        modifiedAt = post.modifiedAt,
        isModified = post.isModified,
        modifier = Modifier.clickable { onPost(post.link, post.title) }
    )

    CommentView(
        content = comment.content,
        handle = comment.author.handle,
        avatar = comment.author.avatar,
        rank = comment.author.rank,
        createdAt = comment.createdAt,
        modifier = Modifier.clickable { onPost(comment.link, post.title) }
    )

    SeeAllCommentsView(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AlgoismeTheme.shapes.medium)
            .background(AlgoismeTheme.colors.primaryVariant)
            .padding(horizontal = 12.dp)
            .height(32.dp)
            .clickable { onPost(post.link, post.title) }
    )
}

@Composable
private fun CommentView(
    content: String,
    handle: String,
    avatar: String,
    rank: String?,
    createdAt: Long,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier.padding(horizontal = 12.dp),
    horizontalArrangement = Arrangement.spacedBy(6.dp),
) {
    UserAvatar(avatar, Modifier.border(1.dp, colorResource(getColorByUserRank(rank)), CircleShape))

    Column(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 15.dp, bottomEnd = 15.dp, bottomStart = 15.dp))
            .background(AlgoismeTheme.colors.primaryVariant)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = buildAgoText(handle, rank, createdAt),
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant
        )

        Text(
            text = content,
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Composable
private fun buildAgoText(userHandle: String, userRank: String?, time: Long) = buildAnnotatedString {
    val handle = colorTextByUserRankNew(userHandle, userRank)
    val postState = CwApp.app.getString(R.string.created)

    return handle + AnnotatedString(" • $postState ${PrettyTime().format(Date(time * 1000))}")
}