package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun AnnotatedClickableText(
    text: String = "",
    textStyle: TextStyle = MaterialTheme.typography.body1,
    textColor: Color = MaterialTheme.colors.secondaryVariant,
    clickableText: String = "",
    clickableTextStyle: TextStyle = MaterialTheme.typography.body2,
    clickableTextColor: Color = MaterialTheme.colors.onBackground,
    onClick: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        withStyle(clickableTextStyle.toSpanStyle()) {
            append(clickableText)
        }
    }
    Row {
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
        Text(
            text = if (text.isNotEmpty() and annotatedText.isNotEmpty()) " " else "",
            style = textStyle
        )
        ClickableText(
            text = annotatedText,
            style = TextStyle(
                color = clickableTextColor
            ),
            onClick = { onClick() }
        )
    }
}