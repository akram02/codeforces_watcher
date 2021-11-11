package io.xorum.codeforceswatcher.features.verification.redux

import io.xorum.codeforceswatcher.features.auth.models.UserAccount
import io.xorum.codeforceswatcher.features.verification.VerificationRepository
import io.xorum.codeforceswatcher.redux.*
import io.xorum.codeforceswatcher.util.Response
import tw.geothings.rekotlin.Action

class VerificationRequests {

    class VerifyCodeforces(private val handle: String) : Request() {

        private val verificationRepository = VerificationRepository()

        override suspend fun execute() {
            when (val response = verificationRepository.verifyCodeforcesAccount(handle)) {
                is Response.Success -> {
                    store.dispatch(Success(response.result))
                }
                is Response.Failure -> store.dispatch(Failure(response.error))
            }
        }

        data class Success(val userAccount: UserAccount) : Action
        data class Failure(val message: String?) : Action
    }

    class FetchVerificationCode : Request() {

        private val verificationRepository = VerificationRepository()

        override suspend fun execute() {
            val response = verificationRepository.fetchCodeforcesVerificationCode()
            if (response is Response.Success) store.dispatch(Success(response.result.code))
        }

        data class Success(val verificationCode: String) : Action
    }

    object ResetVerificationCodeforcesMessage: Action
}
