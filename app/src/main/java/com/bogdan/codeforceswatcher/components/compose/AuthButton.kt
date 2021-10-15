package com.bogdan.codeforceswatcher.components.compose

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AuthButton(label: String) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(100)),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
        onClick = { /*TODO*/ }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.button
        )
    }
}
