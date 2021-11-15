package io.xorum.codeforceswatcher.redux

import io.xorum.codeforceswatcher.util.Strings
import tw.geothings.rekotlin.Action

interface ToastAction : Action {

    val message: Message
}

sealed class Message {

    object NoConnection : Message()

    object UserAlreadyAdded : Message()

    object None : Message()

    data class Custom(val message: String) : Message()
}

fun String?.toMessage() =
    if (this.isNullOrEmpty()) Message.NoConnection
    else Message.Custom(this)

fun Message.handle() =
    when (this) {
        is Message.NoConnection -> Strings.get("check_connection_or_try_again_later")
        is Message.UserAlreadyAdded -> Strings.get("user_already_added")
        is Message.None -> null
        is Message.Custom -> this.message
    }