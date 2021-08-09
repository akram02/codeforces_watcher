package io.xorum.codeforceswatcher.features.problems.response

import io.xorum.codeforceswatcher.features.problems.models.Problem
import kotlinx.serialization.Serializable

@Serializable
internal data class ApiProblem(
    val id: String,
    val title: String,
    val subtitle: String,
    val platform: String,
    val link: String,
    val createdAtMillis: Long,
    val tags: List<String>,
) {

    fun toProblem(isFavourite: Boolean): Problem? {
        val platform = parsePlatform() ?: return null
        return Problem(
            id = id,
            title = title,
            subtitle = subtitle,
            platform = platform,
            link = link,
            createdAtMillis = createdAtMillis,
            tags = tags,
            isFavourite = isFavourite
        )
    }

    private fun parsePlatform() = when (platform) {
        "CODEFORCES" -> Problem.Platform.CODEFORCES
        else -> null
    }
}