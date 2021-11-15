package io.xorum.codeforceswatcher.features.auth.redux

import io.xorum.codeforceswatcher.redux.states.AppState
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
                status = AuthState.Status.DONE
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
                signInMessage = null
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
                status = AuthState.Status.IDLE,
                signUpMessage = action.message
            )
        }
        is AuthRequests.ResetSignUpMessage -> {
            newState = newState.copy(
                signUpMessage = null
            )
        }
        is AuthRequests.SendPasswordReset -> {
            newState = newState.copy(
                status = AuthState.Status.PENDING
            )
        }
        is AuthRequests.SendPasswordReset.Success -> {
            newState = newState.copy(
                status = AuthState.Status.DONE
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
                restorePasswordMessage = null
            )
        }
        is AuthRequests.UpdateAuthStage -> {
            newState = newState.copy(
                authStage = action.authStage
            )
        }
        is AuthRequests.LogOut.Success -> {
            newState = AuthState()
        }
    }

    return newState
}
