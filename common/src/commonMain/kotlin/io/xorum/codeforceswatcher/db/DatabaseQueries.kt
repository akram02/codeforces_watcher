package io.xorum.codeforceswatcher.db

import io.xorum.codeforceswatcher.features.contests.models.Contest
import io.xorum.codeforceswatcher.features.contests.models.Platform
import io.xorum.codeforceswatcher.features.problems.models.Problem
import io.xorum.codeforceswatcher.features.users.models.RatingChange
import io.xorum.codeforceswatcher.features.users.models.User
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

internal object DatabaseQueries {

    internal object Users {

        fun getAll() = database.userQueries.getAll().executeAsList().map { User.fromDB(it) }

        fun insert(user: User) {
            val serializer = Json(from = Json.Default) { ignoreUnknownKeys = true }
            val ratingChangesJson = serializer.encodeToString(ListSerializer(RatingChange.serializer()), user.ratingChanges)
            database.userQueries.insert(user.id, user.avatar, user.rank, user.handle, user.rating?.toLong(), user.maxRating?.toLong(), user.firstName, user.lastName, ratingChangesJson, user.maxRank, user.contribution)
        }

        fun update(user: User) {
            val serializer = Json(from = Json.Default) { ignoreUnknownKeys = true }
            val ratingChangesJson = serializer.encodeToString(ListSerializer(RatingChange.serializer()), user.ratingChanges)
            database.userQueries.update(user.id, user.avatar, user.rank, user.handle, user.rating?.toLong(), user.maxRating?.toLong(), user.firstName, user.lastName, ratingChangesJson, user.maxRank, user.contribution)
        }

        fun update(users: List<User>) {
            database.userQueries.transaction {
                users.forEach { user ->
                    update(user)
                }
            }
        }

        fun insert(users: List<User>) {
            database.userQueries.transaction {
                users.forEach { user ->
                    insert(user)
                }
            }
        }

        fun delete(userId: Long) = database.userQueries.delete(userId)
    }

    internal object Contests {

        fun getAll() = database.contestQueries.getAll().executeAsList().map { Contest.fromDB(it) }

        fun insert(contests: List<Contest>) {
            database.contestQueries.transaction {
                contests.filter { it.platform == Platform.CODEFORCES }.forEach { contest ->
                    database.contestQueries.insert(contest.id, contest.name, contest.startTimeSeconds, contest.durationSeconds, contest.phase)
                }
            }
        }

        fun deleteAll() = database.contestQueries.deleteAll()
    }

    internal object Problems {

        fun getAll() = database.problemQueries.getAll().executeAsList().map { Problem.fromDB(it) }

        fun insert(problems: List<Problem>): List<Long> {
            database.problemQueries.transaction {
                for (problem in problems) {
                    insert(problem)
                }
            }

            val databaseProblems = getAll()
            val identifiers = databaseProblems.associate { it.identify() to it.id }
            val resultIds = mutableListOf<Long>()
            problems.forEach { identifiers[it.identify()]?.let { id -> resultIds.add(id) } }
            return resultIds
        }

        fun insert(problem: Problem) = if (problem.id == 0L) {
            database.problemQueries.insert(problem.name, problem.enName, problem.ruName, problem.index, problem.contestId, problem.contestName, problem.contestTime, problem.isFavourite)
        } else {
            database.problemQueries.update(problem.id, problem.name, problem.enName, problem.ruName, problem.index, problem.contestId, problem.contestName, problem.contestTime, problem.isFavourite)
        }

        fun deleteAll() = database.problemQueries.deleteAll()
    }
}
