package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algoisme.ui.theme.GrayDay
import com.example.algoisme.ui.theme.MainDay
import com.example.algoisme.ui.theme.defaultTextStyle

@Composable
fun AuthTextField(
    label: String,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    var value by remember {
        mutableStateOf("")
    }
    val modifier = Modifier.fillMaxWidth()

    Column(modifier = modifier) {
        Text(
            text = if (value.isNotEmpty()) label else "",
            modifier = modifier.height(17.dp),
            style = defaultTextStyle,
            fontSize = 13.sp,
            color = GrayDay
        )
        BasicTextField(
            value = value,
            onValueChange = { value = it },
            textStyle = defaultTextStyle,
            singleLine = true,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = label,
                        modifier = modifier.height(19.dp),
                        style = defaultTextStyle,
                        color = GrayDay,
                    )
                }
                innerTextField()
            },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )

        Spacer(Modifier.height(2.dp))

        Canvas(modifier = modifier) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            drawLine(
                start = Offset(x = canvasWidth, y = 0f),
                end = Offset(x = 0f, y = canvasHeight),
                color = MainDay,
                strokeWidth = 1f
            )
        }
    }
}
