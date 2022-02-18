package com.bogdan.codeforceswatcher.components.compose.textfields

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

@Composable
fun AuthTextField(
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit
) {
    var value by remember { mutableStateOf("") }

    Column {
        Text(
            text = if (value.isNotEmpty()) label else "",
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant
        )
        BasicTextField(
            value = value,
            onValueChange = {
                value = it
                onValueChange(it)
            },
            modifier = modifier.fillMaxWidth(),
            textStyle = AlgoismeTheme.typography.primaryRegular.copy(color = AlgoismeTheme.colors.onBackground),
            singleLine = true,
            cursorBrush = SolidColor(AlgoismeTheme.colors.onBackground),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = label,
                        style = AlgoismeTheme.typography.primaryRegular,
                        color = AlgoismeTheme.colors.secondaryVariant,
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
            if (value.isEmpty()) AlgoismeTheme.colors.secondaryVariant else AlgoismeTheme.colors.onBackground
        Canvas(modifier.fillMaxWidth()) {
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
