package io.xorum.codeforceswatcher.features.auth.redux

import tw.geothings.rekotlin.StateType

data class AuthState(
    val status: Status = Status.IDLE,
    val authStage: Stage = Stage.NOT_SIGNED_IN,
    val signUpMessage: String = "",
    val signInMessage: String? = null,
    val restorePasswordMessage: String? = null
) : StateType {

    enum class Status { IDLE, PENDING, DONE }
    enum class Stage { NOT_SIGNED_IN, SIGNED_IN, VERIFIED }
}