package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.theme.MUButtonSemiBold16

@Composable
fun AuthButton(label: String) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(100)),
//        colors = ButtonDefaults.buttonColors(backgroundColor = Black),
        onClick = { /*TODO*/ }
    ) {
        Text(
            text = label,
            style = MUButtonSemiBold16
//            style = defaultTextStyle,
//            fontSize = 16.sp,
//            fontWeight = FontWeight.SemiBold,
//            color = White
        )
    }
}
