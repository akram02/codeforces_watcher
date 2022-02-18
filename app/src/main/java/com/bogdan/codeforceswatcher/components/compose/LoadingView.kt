package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@ExperimentalComposeUiApi
@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(AlgoismeTheme.colors.onBackground.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = AlgoismeTheme.colors.background)
        }
    }
}