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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.MUHintRegular13
import com.bogdan.codeforceswatcher.components.compose.theme.MUPrimaryRegular16

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
            style = MUHintRegular13
//            style = defaultTextStyle,
//            fontSize = 13.sp,
//            color = DoveGray
        )
        BasicTextField(
            value = value,
            onValueChange = { value = it },
            textStyle = MUPrimaryRegular16,
//            textStyle = defaultTextStyle,
            singleLine = true,
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = label,
                        style = MUPrimaryRegular16,
                        modifier = modifier.height(19.dp),
//                        style = defaultTextStyle,
//                        color = DoveGray,
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
                color = Color.Green,
                strokeWidth = 1f
            )
        }
    }
}
