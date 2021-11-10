package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration

data class LinkTextData(
    val text: String,
    val tag: String? = null,
    val annotation: String? = null,
    val onClick: ((str: AnnotatedString.Range<String>) -> Unit)? = null,
)

@Composable
fun LinkText(
    linkTextData: List<LinkTextData>,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    clickableTextStyle: TextStyle = MaterialTheme.typography.body2,
    paragraphStyle: ParagraphStyle = ParagraphStyle()
) {
    val annotatedString = createAnnotatedString(
        data = linkTextData,
        clickableTextStyle = clickableTextStyle.toSpanStyle(),
        paragraphStyle = paragraphStyle
    )

    ClickableText(
        text = annotatedString,
        style = textStyle,
        onClick = { offset ->
            linkTextData.forEach { annotatedStringData ->
                if (annotatedStringData.tag != null) {
                    annotatedString.getStringAnnotations(
                        tag = annotatedStringData.tag,
                        start = offset,
                        end = offset,
                    ).firstOrNull()?.let {
                        annotatedStringData.onClick?.invoke(it)
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun createAnnotatedString(
    data: List<LinkTextData>,
    clickableTextStyle: SpanStyle,
    paragraphStyle: ParagraphStyle
) = buildAnnotatedString {
    withStyle(paragraphStyle) {
        data.forEach { linkTextData ->
            if (linkTextData.tag != null) {
                pushStringAnnotation(
                    tag = linkTextData.tag,
                    annotation = linkTextData.annotation ?: ""
                )
                withStyle(clickableTextStyle.copy(textDecoration = TextDecoration.Underline)) {
                    append(linkTextData.text)
                }
                pop()
            } else {
                append(linkTextData.text)
            }
        }
    }
}