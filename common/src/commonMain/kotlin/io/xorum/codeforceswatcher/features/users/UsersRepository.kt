package io.xorum.codeforceswatcher.features.users

import io.ktor.client.request.*
import io.xorum.codeforceswatcher.features.users.models.User
import io.xorum.codeforceswatcher.features.users.response.UserData
import io.xorum.codeforceswatcher.util.request

internal class UsersRepository {

    suspend fun fetchUserData(handles: String) = request { httpClient ->
        httpClient.get<UserData>("user/data") {
            parameter("handles", handles)
        }
    }

    suspend fun fetchUser(handle: String) = request { httpClient ->
        httpClient.get<User>("user") {
            parameter("handle", handle)
        }
    }

    suspend fun addUser(handle: String) = request { httpClient ->
        httpClient.post<User>("user/add") {
            parameter("handle", handle)
        }
    }

    suspend fun deleteUser(handle: String) = request { httpClient ->
        httpClient.post<Unit>("user/delete") {
            parameter("handle", handle)
        }
    }

    suspend fun deleteAccount() = request { httpClient ->
        httpClient.post<Unit>("user/delete-account")
    }
}