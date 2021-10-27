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

        override suspend fun execute() = firebaseController.signIn(email, password) { exception ->
            exception?.let {
                store.dispatch(Failure(Strings.get("wrong_credentials")))
            } ?: store.dispatch(Success)
        }

        object Success : Action
        data class Failure(val message: String) : Action
    }

    class SignUp(
        private val email: String,
        private val password: String
    ) : Request() {

        override suspend fun execute() = firebaseController.signUp(email, password) { exception ->
            exception?.let {
                store.dispatch(Failure(exception.message.toMessage()))
            } ?: store.dispatch(Success)
        }

        object Success : Action
        data class Failure(override val message: Message) : ToastAction
    }

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
                store.dispatch(Failure(Strings.get("user_does_not_exist")))
                return
            }
            firebaseController.sendPasswordReset(email) { exception ->
                exception?.let {
                    store.dispatch(Failure(Strings.get("user_does_not_exist")))
                } ?: store.dispatch(Success(""))
            }
        }

        data class Success(override val message: Message) : ToastAction
        data class Failure(override val message: Message) : ToastAction
    }
}
