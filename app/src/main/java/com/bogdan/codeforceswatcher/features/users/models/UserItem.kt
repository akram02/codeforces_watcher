package com.bogdan.codeforceswatcher.features.users

import android.graphics.Color.*
import android.text.Spannable
import android.text.SpannableString
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import com.bogdan.codeforceswatcher.CwApp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.users.compose.AvatarView
import com.bogdan.codeforceswatcher.features.users.compose.DefaultAvatarView
import com.bogdan.codeforceswatcher.util.colorSubstring
import io.xorum.codeforceswatcher.features.users.models.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

enum class Update { INCREASE, DECREASE, NULL }

data class UserItem(private val user: User) {

    val id: Long = user.id
    val avatar = user.avatar
    var update: Update = Update.NULL
    val handle: String = user.handle
    val rating: String = user.rating?.toString().orEmpty()
    var lastRatingUpdate: String = ""
    var dateOfLastRatingUpdate: String = CwApp.app.getString(R.string.no_activity)
    val rankColor: Int = getColorByUserRank(user.rank)
    val rank = user.rank

    init {
        user.ratingChanges.lastOrNull()?.let { ratingChange ->
            dateOfLastRatingUpdate = CwApp.app.getString(
                R.string.last_activity,
                getDateTime(ratingChange.ratingUpdateTimeSeconds)
            )
            val difference = ratingChange.newRating - ratingChange.oldRating
            update = if (difference >= 0) Update.INCREASE else Update.DECREASE
            lastRatingUpdate = abs(difference).toString()
        }
    }

    private fun getDateTime(seconds: Long): String {
        val dateFormat =
            SimpleDateFormat(CwApp.app.getString(R.string.user_date_format), Locale.getDefault())
        return dateFormat.format(Date(seconds * 1000)).toString()
    }

    // Needed for disable flicking of epoxy model when all ratingChanges fetched
    override fun toString() =
        "$id$avatar$update$handle$rating$lastRatingUpdate$dateOfLastRatingUpdate$rankColor$rank"
}

fun getColorByUserRank(rank: String?) = when (rank) {
    null -> R.color.black

    "newbie" -> R.color.gray
    "новичок" -> R.color.gray

    "pupil" -> R.color.green
    "ученик" -> R.color.green

    "specialist" -> R.color.blue_green
    "специалист" -> R.color.blue_green

    "expert" -> R.color.blue
    "эксперт" -> R.color.blue

    "candidate master" -> R.color.purple
    "кандидат в мастера" -> R.color.purple

    "master" -> R.color.orange
    "мастер" -> R.color.orange

    "international master" -> R.color.orange
    "международный мастер" -> R.color.orange

    "grandmaster" -> R.color.red
    "гроссмейстер" -> R.color.red

    "international grandmaster" -> R.color.red
    "международный гроссмейстер" -> R.color.red

    "legendary grandmaster" -> R.color.red
    "легендарный гроссмейстер" -> R.color.red

    else -> R.color.gray
}

fun adjustColor(color: Int, factor: Float, alpha: Float = 1f): Color {
    val a = min(max(round(color.alpha * alpha).toInt(), 0), 255)
    val r = min(max(round(color.red + 255f * factor).toInt(), 0), 255)
    val g = min(max(round(color.green + 255f * factor).toInt(), 0), 255)
    val b = min(max(round(color.blue + 255f * factor).toInt(), 0), 255)
    val newColor = argb(a, r, g, b).toLong()
    val hexColor = String.format("#%08X", 0xFFFFFFFF and newColor)
    return Color(parseColor(hexColor))
}

fun colorTextByUserRank(text: String, rank: String?) =
    if (listOf("legendary grandmaster", "легендарный гроссмейстер").contains(rank)) {
        val colorText = "<font color=black>${text[0]}</font><font color=red>${
            text.subSequence(1, text.length)
        }</font>"
        SpannableString(HtmlCompat.fromHtml(colorText, HtmlCompat.FROM_HTML_MODE_LEGACY))
    } else SpannableString(text).apply {
        colorSubstring(0, text.length, getColorByUserRank(rank))
    }

@Composable
fun colorTextByUserRankNew(text: String, rank: String?) = buildAnnotatedString {
    if (listOf("legendary grandmaster", "легендарный гроссмейстер").contains(rank)) {
        withStyle(SpanStyle(color = AlgoismeTheme.colors.secondary)) { append(text[0]) }
        withStyle(SpanStyle(color = colorResource(getColorByUserRank(rank)))) {
            append(text.substring(1, text.length))
        }
    } else {
        withStyle(SpanStyle(color = colorResource(getColorByUserRank(rank)))) {
            append(text)
        }
    }
}

@Composable
fun UserAvatar(
    avatar: String,
    modifier: Modifier
) {
    if (avatar == "https://userpic.codeforces.org/no-avatar.jpg") {
        DefaultAvatarView(modifier)
    } else {
        AvatarView(avatar, modifier)
    }
}