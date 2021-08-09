package io.xorum.codeforceswatcher.features.problems.response

import kotlinx.serialization.Serializable

@Serializable
internal data class ProblemsAndTagsResponse(
        val problems: List<ApiProblem>,
        val tags: List<String>
)