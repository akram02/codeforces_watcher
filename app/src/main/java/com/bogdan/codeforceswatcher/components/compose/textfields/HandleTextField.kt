package com.bogdan.codeforceswatcher.components.compose.textfields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.bogdan.codeforceswatcher.R

@Composable
fun HandleTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    val localFocusManager = LocalFocusManager.current

    AuthTextField(
        label = stringResource(R.string.codeforces_handle),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { localFocusManager.clearFocus() }
        ),
        modifier = modifier
    ) {
        onValueChange(it)
    }
}