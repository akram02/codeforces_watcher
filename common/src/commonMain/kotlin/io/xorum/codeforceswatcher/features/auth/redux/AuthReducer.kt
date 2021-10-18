package io.xorum.codeforceswatcher.features.auth.redux

import io.xorum.codeforceswatcher.redux.states.AppState
import io.xorum.codeforceswatcher.util.Strings
import tw.geothings.rekotlin.Action

fun authReducer(action: Action, state: AppState): AuthState {
    var newState = state.auth

    when (action) {
        is AuthRequests.SignIn -> {
            newState = newState.copy(
                status = AuthState.Status.PENDING
            )
        }
        is AuthRequests.SignIn.Success -> {
            newState = newState.copy(
                status = AuthState.Status.DONE,
                signInMessage = action.message
            )
        }
        is AuthRequests.SignIn.Failure -> {
            newState = newState.copy(
                status = AuthState.Status.IDLE,
                signInMessage = action.message
            )
        }
        is AuthRequests.ResetSignInMessage -> {
            newState = newState.copy(
                signInMessage = ""
            )
        }
        is AuthRequests.SignUp -> {
            newState = newState.copy(
                status = AuthState.Status.PENDING
            )
        }
        is AuthRequests.SignUp.Success -> {
            newState = newState.copy(
                status = AuthState.Status.DONE
            )
        }
        is AuthRequests.SignUp.Failure -> {
            newState = newState.copy(
                status = AuthState.Status.IDLE
            )
        }
        is AuthRequests.SendPasswordReset -> {
            newState = newState.copy(
                status = AuthState.Status.PENDING
            )
        }
        is AuthRequests.SendPasswordReset.Success -> {
            newState = newState.copy(
                status = AuthState.Status.DONE,
                restorePasswordMessage = action.message
            )
        }
        is AuthRequests.SendPasswordReset.Failure -> {
            newState = newState.copy(
                status = AuthState.Status.IDLE,
                restorePasswordMessage = action.message
            )
        }
        is AuthRequests.ResetRestorePasswordMessage -> {
            newState = newState.copy(
                restorePasswordMessage = ""
            )
        }
        is AuthRequests.UpdateAuthStage -> {
            newState = newState.copy(authStage = action.authStage)
        }
        is AuthRequests.LogOut.Success -> {
            newState = AuthState()
        }
    }

    return newState
}
