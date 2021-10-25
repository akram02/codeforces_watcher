package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.Box
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
    val onClick: (() -> Unit)? = null,
)

@Composable
fun LinkText(
    linkTextData: List<LinkTextData>,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    clickableTextStyle: TextStyle = MaterialTheme.typography.body2
) {
    val annotatedString = createAnnotatedString(linkTextData, clickableTextStyle.toSpanStyle())

    ClickableText(
        text = annotatedString,
        style = textStyle,
        onClick = { offset ->
            linkTextData.forEach { annotatedStringData ->
                if (annotatedStringData.onClick != null) {
                    annotatedString.getStringAnnotations(
                        start = offset,
                        end = offset,
                    ).firstOrNull()?.let {
                        annotatedStringData.onClick.invoke()
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun createAnnotatedString(data: List<LinkTextData>, style: SpanStyle): AnnotatedString {
    return buildAnnotatedString {
        data.forEach { linkTextData ->
            if (linkTextData.tag != null && linkTextData.annotation != null) {
                pushStringAnnotation(
                    tag = linkTextData.tag,
                    annotation = linkTextData.annotation
                )
                withStyle(
                    style = style.copy(textDecoration = TextDecoration.Underline)
                ) {
                    append(linkTextData.text)
                }
            } else {
                append(linkTextData.text)
            }
        }
    }
}