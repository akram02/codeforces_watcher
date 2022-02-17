package com.bogdan.codeforceswatcher.components.compose.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

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
        textStyle = AlgoismeTheme.typography.buttonSemiBold.copy(fontSize = 14.sp),
        backgroundColor = if (isFollowed) AlgoismeTheme.colors.secondary else AlgoismeTheme.colors.primary,
        labelColor = if (isFollowed) AlgoismeTheme.colors.primary else AlgoismeTheme.colors.secondary,
        borderStroke = if (isFollowed) BorderStroke(2.dp, AlgoismeTheme.colors.secondary) else null
    ) {
        action()
    }
}