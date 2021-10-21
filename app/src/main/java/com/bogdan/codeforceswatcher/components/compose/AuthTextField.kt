package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AuthTextField(
    label: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }
    val modifier = Modifier.fillMaxWidth()

    Column(modifier = modifier) {
        Text(
            text = if (value.isNotEmpty()) label else "",
            modifier = modifier.height(17.dp),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.secondaryVariant
        )
        BasicTextField(
            value = value,
            onValueChange = {
                value = it
                onValueChange(it)
            },
            modifier = modifier,
            textStyle = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onBackground),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colors.onBackground),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.secondaryVariant,
                        modifier = modifier.height(19.dp)
                    )
                }
                innerTextField()
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation
        )

        Spacer(Modifier.height(2.dp))

        val lineColor =
            if (value.isEmpty()) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.onBackground
        Canvas(modifier = modifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0f, y = canvasHeight),
                color = lineColor,
                strokeWidth = 2f
            )
        }
    }
}
