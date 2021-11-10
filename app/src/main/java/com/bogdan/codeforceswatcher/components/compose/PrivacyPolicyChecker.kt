package com.bogdan.codeforceswatcher.components.compose

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import io.xorum.codeforceswatcher.util.Constants.PRIVACY_POLICY_LINK
import io.xorum.codeforceswatcher.util.Constants.TERMS_AND_CONDITIONS_LINK

@Composable
fun PrivacyPolicyChecker(
    modifier: Modifier = Modifier,
    isAccepted: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    clickableTextStyle: TextStyle = MaterialTheme.typography.body2,
    paragraphStyle: ParagraphStyle = ParagraphStyle(),
    onTermsAndConditionsClick: (() -> Unit)? = null,
    onPrivacyPolicyClick: (() -> Unit)? = null,
    onCheckboxClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkboxIcon =
            if (isAccepted) R.drawable.ic_checkbox else R.drawable.ic_checkbox_disabled

        Icon(
            painter = painterResource(id = checkboxIcon),
            contentDescription = "Checkbox",
            modifier = Modifier.clickable { onCheckboxClick() },
            tint = if (isAccepted) MaterialTheme.colors.onBackground else MaterialTheme.colors.secondaryVariant
        )

        Spacer(Modifier.width(12.dp))

        LinkText(
            linkTextData = listOf(
                LinkTextData("${stringResource(R.string.agree_with_the_conditions_and_privacy_policy_start)} "),
                LinkTextData(
                    text = stringResource(R.string.terms_and_conditions),
                    tag = "terms_and_conditions",
                    annotation = TERMS_AND_CONDITIONS_LINK
                ) {
                    onTermsAndConditionsClick?.invoke()
                },
                LinkTextData(" ${stringResource(R.string.agree_with_the_conditions_and_privacy_policy_end)} "),
                LinkTextData(
                    text = stringResource(R.string.privacy_policy),
                    tag = "privacy_policy",
                    annotation = PRIVACY_POLICY_LINK,
                ) {
                    onPrivacyPolicyClick?.invoke()
                },
            ),
            textStyle = textStyle,
            clickableTextStyle = clickableTextStyle,
            paragraphStyle = paragraphStyle
        )
    }
}