package com.bogdan.codeforceswatcher.features.users

import android.text.SpannableString
import io.xorum.codeforceswatcher.features.users.models.User
import androidx.core.text.HtmlCompat
import com.bogdan.codeforceswatcher.CwApp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.util.colorSubstring
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

enum class Update { INCREASE, DECREASE, NULL }

data class UserItem(private val user: User) {

    val id: Long = user.id
    val avatar = if (user.avatar == "https://userpic.codeforces.org/no-avatar.jpg") {
        R.drawable.ic_default_avatar
    } else {
        user.avatar
    }
    var update: Update = Update.NULL
    val handle: SpannableString = colorTextByUserRank(user.handle, user.rank)
    val rating: SpannableString = colorTextByUserRank(user.rating?.toString().orEmpty(), user.rank)
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

fun colorTextByUserRank(text: String, rank: String?) =
    if (listOf("legendary grandmaster", "легендарный гроссмейстер").contains(rank)) {
        val colorText = "<font color=black>${text[0]}</font><font color=red>${
            text.subSequence(1, text.length)
        }</font>"
        SpannableString(HtmlCompat.fromHtml(colorText, HtmlCompat.FROM_HTML_MODE_LEGACY))
    } else SpannableString(text).apply {
        colorSubstring(0, text.length, getColorByUserRank(rank))
    }