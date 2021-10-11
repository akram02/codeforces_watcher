package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.algoisme.ui.theme.MainDay
import com.example.algoisme.ui.theme.defaultTextStyle

@Composable
fun Title(title: String) {
    Text(
        text = title,
        modifier = Modifier.fillMaxWidth(),
        style = defaultTextStyle,
        fontSize = 40.sp,
        fontWeight = FontWeight(500),
        color = MainDay,
        textAlign = TextAlign.Start
    )
}