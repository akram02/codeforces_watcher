package com.bogdan.codeforceswatcher.components.compose.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.LinkText
import com.bogdan.codeforceswatcher.components.compose.LinkTextData

@Composable
fun PrivacyPolicyChecker(
    modifier: Modifier = Modifier,
    isAccepted: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    clickableTextStyle: TextStyle = MaterialTheme.typography.body2,
    paragraphStyle: ParagraphStyle = ParagraphStyle(),
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkboxIcon = if (isAccepted) R.drawable.ic_checkbox else R.drawable.ic_checkbox_disabled

        Icon(
            painter = painterResource(id = checkboxIcon),
            contentDescription = "Checkbox",
            modifier = Modifier.clickable { onClick() }
        )

        Spacer(Modifier.width(12.dp))

        LinkText(
            linkTextData = listOf(
                LinkTextData("I agree with the "),
                LinkTextData("Terms and Conditions ") { },
                LinkTextData("and the "),
                LinkTextData("Privacy Policy") { },
            ),
            textStyle = textStyle,
            clickableTextStyle = clickableTextStyle,
            paragraphStyle = paragraphStyle
        )
    }
}