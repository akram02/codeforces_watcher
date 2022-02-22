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
    post: News.PostWithComment,
    onPost: (String, String) -> Unit
) = Column(
    modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(AlgoismeTheme.colors.lightGray),
    verticalArrangement = Arrangement.spacedBy(10.dp)
) {
    PostContentView(
        post = post.post,
        modifier = Modifier.clickable { onPost(post.post.link, post.post.title) }
    )

    CommentView(
        comment = post.comment,
        modifier = Modifier.clickable { onPost(post.comment.link, post.post.title) }
    )

    SeeAllCommentsView(
        modifier = Modifier
            .fillMaxWidth()
            .clip(AlgoismeTheme.shapes.medium)
            .background(AlgoismeTheme.colors.primaryVariant)
            .padding(horizontal = 12.dp)
            .height(32.dp)
            .clickable { onPost(post.post.link, post.post.title) }
    )
}

@Composable
private fun CommentView(
    comment: News.Comment,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
        .padding(horizontal = 12.dp),
    horizontalArrangement = Arrangement.spacedBy(6.dp),
) {
    UserAvatar(
        avatar = comment.author.avatar,
        modifier = Modifier.border(1.dp, colorResource(getColorByUserRank(comment.author.rank)), CircleShape)
    )

    Column(
        modifier = Modifier
            .weight(1f)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 15.dp,
                    bottomEnd = 15.dp,
                    bottomStart = 15.dp
                )
            )
            .background(AlgoismeTheme.colors.primaryVariant)
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = buildPostAgoText(comment.author, comment.createdAt),
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant
        )

        Text(
            text = comment.content,
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

@Composable
private fun buildPostAgoText(user: News.User, time: Long) = buildAnnotatedString {
    val handle = colorTextByUserRankNew(user.handle, user.rank)

    return handle + AnnotatedString(" • ${PrettyTime().format(Date(time * 1000))}")
}