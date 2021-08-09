package io.xorum.codeforceswatcher.features.contests

import io.ktor.client.request.*
import io.xorum.codeforceswatcher.features.contests.response.ApiContest
import io.xorum.codeforceswatcher.util.request

internal class ContestsRepository {

    suspend fun getAll() = request { httpClient ->
        httpClient.get<List<ApiContest>>(path = "contests")
    }
}