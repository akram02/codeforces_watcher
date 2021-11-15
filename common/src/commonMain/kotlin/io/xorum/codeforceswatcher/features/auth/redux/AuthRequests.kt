package io.xorum.codeforceswatcher.features.auth.redux

import io.xorum.codeforceswatcher.features.notifications.NotificationsRepository
import io.xorum.codeforceswatcher.redux.*
import io.xorum.codeforceswatcher.util.Strings
import tw.geothings.rekotlin.Action

class AuthRequests {

    class SignIn(
        private val email: String,
        private val password: String
    ) : Request() {

        override suspend fun execute() {
            if (email.isEmpty() || password.isEmpty()) {
                store.dispatch(Failure(Strings.get("fields_cannot_be_empty")))
                return
            }
            firebaseController.signIn(email, password) { exception ->
                exception?.let {
                    store.dispatch(Failure(exception.message.toString()))
                } ?: store.dispatch(Success)
            }
        }

        object Success : Action
        data class Failure(val message: String) : Action
    }

    object ResetSignInMessage : Action

    class SignUp(
        private val email: String,
        private val password: String,
        private val confirmPassword: String,
        private val isPrivacyPolicyAccepted: Boolean
    ) : Request() {

        override suspend fun execute() {
            if (!isPrivacyPolicyAccepted) {
                store.dispatch(Failure(Strings.get("agree_to_the_privacy_policy")))
                return
            }
            if (password.isEmpty() || email.isEmpty() || confirmPassword.isEmpty()) {
                store.dispatch(Failure(Strings.get("fields_cannot_be_empty")))
                return
            }
            if (password != confirmPassword) {
                store.dispatch(Failure(Strings.get("passwords_do_not_match")))
                return
            }
            firebaseController.signUp(email, password) { exception ->
                exception?.let {
                    store.dispatch(Failure(exception.message.toString()))
                } ?: store.dispatch(Success)
            }
        }

        object Success : Action
        data class Failure(val message: String?) : Action
    }

    object ResetSignUpMessage : Action

    data class UpdateAuthStage(val authStage: AuthState.Stage) : Action

    object LogOut : Request() {

        private val notificationsRepository = NotificationsRepository()

        override suspend fun execute() {
            pushToken?.let {
                notificationsRepository.deletePushToken(it)
            }
            firebaseController.logOut { exception ->
                exception?.let {
                    store.dispatch(Failure(it.message.toMessage()))
                } ?: store.dispatch(Success)
            }
        }

        object Success : Action
        data class Failure(override val message: Message) : ToastAction
    }

    class SendPasswordReset(private val email: String) : Request() {

        override suspend fun execute() {
            if (email.isEmpty()) {
                store.dispatch(Failure(Strings.get("fields_cannot_be_empty")))
                return
            }
            firebaseController.sendPasswordReset(email) { exception ->
                exception?.let {
                    store.dispatch(Failure(exception.message.toString()))
                } ?: store.dispatch(Success)
            }
        }

        object Success : Action
        data class Failure(val message: String) : Action
    }

    object ResetRestorePasswordMessage : Action
}
