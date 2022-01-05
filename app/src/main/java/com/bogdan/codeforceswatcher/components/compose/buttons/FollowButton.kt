package com.bogdan.codeforceswatcher.components.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FollowButton(
    label: String,
    modifier: Modifier = Modifier,
    isFollowed: Boolean = false,
    action: () -> Unit
) {
    CommonButton(
        label = label,
        modifier = modifier.defaultMinSize(minWidth = 90.dp, minHeight = 36.dp),
        textStyle = MaterialTheme.typography.button.copy(fontSize = 14.sp),
        backgroundColor = if (isFollowed) MaterialTheme.colors.secondary else MaterialTheme.colors.primary,
        borderStroke = if (isFollowed) BorderStroke(2.dp, MaterialTheme.colors.secondary) else null
    ) {
        action()
    }
}