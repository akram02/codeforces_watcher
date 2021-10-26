package io.xorum.codeforceswatcher.features.verification.redux

import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.states.AppState
import tw.geothings.rekotlin.Action

fun verificationReducer(action: Action, state: AppState): VerificationState {
    var newState = state.verification

    when (action) {
        is VerificationRequests.FetchVerificationCode -> {
            newState = newState.copy(
                status = VerificationState.Status.PENDING
            )
        }
        is VerificationRequests.FetchVerificationCode.Success -> {
            newState = newState.copy(
                status = VerificationState.Status.IDLE,
                verificationCode = action.verificationCode
            )
        }
        is VerificationRequests.VerifyCodeforces -> {
            newState = newState.copy(
                status = VerificationState.Status.PENDING
            )
        }
        is VerificationRequests.VerifyCodeforces.Success -> {
            newState = newState.copy(
                status = VerificationState.Status.DONE,
                message = action.message
            )
        }
        is VerificationRequests.VerifyCodeforces.Failure -> {
            newState = newState.copy(
                status = VerificationState.Status.IDLE,
                message = action.message
            )
        }
        is VerificationRequests.ResetVerificationCodeforcesMessage -> {
            newState = newState.copy(
                status = VerificationState.Status.IDLE,
                message = ""
            )
        }
    }

    return newState
}
