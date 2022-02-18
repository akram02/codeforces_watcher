package com.bogdan.codeforceswatcher.components.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme


@Composable
fun CommonButton(
    label: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = AlgoismeTheme.typography.buttonSemiBold,
    backgroundColor: Color = AlgoismeTheme.colors.secondary,
    labelColor: Color = AlgoismeTheme.colors.primary,
    borderStroke: BorderStroke? = null,
    action: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        elevation = ButtonDefaults.elevation(0.dp),
        border = borderStroke,
        shape = AlgoismeTheme.shapes.small,
        onClick = { action() }
    ) {
        Text(
            text = label,
            style = textStyle,
            color = labelColor
        )
    }
}