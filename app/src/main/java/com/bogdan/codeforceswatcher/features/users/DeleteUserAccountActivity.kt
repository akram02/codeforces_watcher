package com.bogdan.codeforceswatcher.features.users

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

class DeleteUserAccountActivity: ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                ContentView()
            }
        }
    }
}

@Composable
private fun ContentView() = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(AlgoismeTheme.colors.primary)
) {}