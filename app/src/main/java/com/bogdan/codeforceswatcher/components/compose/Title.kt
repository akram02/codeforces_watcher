package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bogdan.codeforceswatcher.components.compose.theme.MUBigHeaderMedium40

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
//        style = defaultTextStyle,
//        fontSize = 40.sp,
//        fontWeight = FontWeight(500),
        style = MUBigHeaderMedium40,
//        color = Color.Unspecified,
    )
}