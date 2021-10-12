package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.bogdan.codeforceswatcher.components.compose.theme.MUHintBoldSemiBold13
import com.bogdan.codeforceswatcher.components.compose.theme.MUHintRegular13

@Composable
fun AnnotatedClickableText(
    text: String = "",
    textStyle: TextStyle = MUHintRegular13,
    clickableText: String = "",
    clickableTextStyle: TextStyle = MUHintBoldSemiBold13,
) {
    val annotatedText = buildAnnotatedString {
        withStyle(clickableTextStyle.toSpanStyle()) {
            append(clickableText)
        }
    }
    Row {
        Text(
            text = text,
            style = textStyle
        )
        Text(
            text = if (text.isNotEmpty() and annotatedText.isNotEmpty()) " " else "",
            style = textStyle
        )
        ClickableText(
            text = annotatedText,
            onClick = { /*TODO*/ }
        )
    }
}