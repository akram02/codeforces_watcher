package io.xorum.codeforceswatcher.features.contests.response

import io.xorum.codeforceswatcher.features.contests.models.Contest
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiContest(
    val platform: String,
    val title: String,
    val link: String,
    val startDateInMillis: Long,
    val durationInMillis: Long,
    val phase: String
) {

    fun toContest(): Contest? {
        val platform = parsePlatform() ?: return null
        val phase = parsePhase() ?: return null

        return Contest(
            platform = platform,
            title = title,
            link = link,
            startDateInMillis = startDateInMillis,
            durationInMillis = durationInMillis,
            phase = phase
        )
    }

    private fun parsePlatform() = when (platform) {
        "CODEFORCES" -> Contest.Platform.CODEFORCES
        "CODEFORCES_GYM" -> Contest.Platform.CODEFORCES_GYM
        "TOPCODER" -> Contest.Platform.TOPCODER
        "ATCODER" -> Contest.Platform.ATCODER
        "CS_ACADEMY" -> Contest.Platform.CS_ACADEMY
        "CODECHEF" -> Contest.Platform.CODECHEF
        "HACKERRANK" -> Contest.Platform.HACKERRANK
        "HACKEREARTH" -> Contest.Platform.HACKEREARTH
        "KICK_START" -> Contest.Platform.KICK_START
        "LEETCODE" -> Contest.Platform.LEETCODE
        "TOPH" -> Contest.Platform.TOPH
        else -> null
    }

    private fun parsePhase() = when (phase) {
        "PENDING" -> Contest.Phase.PENDING
        "RUNNING" -> Contest.Phase.RUNNING
        "FINISHED" -> Contest.Phase.FINISHED
        else -> null
    }
}
