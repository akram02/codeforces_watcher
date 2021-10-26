package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.authReducer
import tw.geothings.rekotlin.Action
import io.xorum.codeforceswatcher.redux.states.AppState

@Composable
fun AuthButton(label: String, action: () -> Unit) {
    Button(
        modifier = Modifier
            .defaultMinSize(minWidth = 250.dp, minHeight = 40.dp)
            .clip(RoundedCornerShape(100)),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        onClick = { action() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.button
        )
    }
}
