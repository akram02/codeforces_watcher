package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algoisme.ui.theme.BackgroundDay
import com.example.algoisme.ui.theme.MainDay
import com.example.algoisme.ui.theme.defaultTextStyle

@Composable
fun AuthButton(label: String) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(100)),
        colors = ButtonDefaults.buttonColors(backgroundColor = MainDay),
        onClick = { /*TODO*/ }
    ) {
        Text(
            text = label,
            style = defaultTextStyle,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = BackgroundDay
        )
    }
}
