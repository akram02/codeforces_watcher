package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AuthButton(
    label: String,
    modifier: Modifier = Modifier,
    isInverted: Boolean = false,
    action: () -> Unit
) {
    Button(
        modifier = modifier
            .defaultMinSize(minWidth = 250.dp, minHeight = 40.dp)
            .clip(RoundedCornerShape(100)),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isInverted) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
        ),
        onClick = { action() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.button
        )
    }
}
