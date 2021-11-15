package com.bogdan.codeforceswatcher.components.compose.textfields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.bogdan.codeforceswatcher.R

@Composable
fun EmailTextField(
    position: TextFieldPosition = TextFieldPosition.NOT_LAST,
    onValueChange: (String) -> Unit
) {
    val localFocusManager = LocalFocusManager.current

    AuthTextField(
        label = stringResource(R.string.email),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = if (position == TextFieldPosition.LAST) ImeAction.Done else ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
            onDone = { localFocusManager.clearFocus() }
        )
    ) {
        onValueChange(it)
    }
}