package com.bogdan.codeforceswatcher.features.users

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun UsersTopBar(
    pickerOptions: Int,
    pickerPosition: Int,
    pickerCallback: (Int) -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .padding(horizontal = 25.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    NavigationBarLeftContent()

    NavigationBarRightContent(pickerOptions, pickerPosition, pickerCallback)
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NavigationBarLeftContent() {
    var isVisibleTitle by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        delay(1000)
        isVisibleTitle = true
    }

    Box {
        AnimatedVisibility(
            visible = !isVisibleTitle,
            exit = fadeOut()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = null
            )
        }

        AnimatedVisibility(
            visible = isVisibleTitle,
            enter = fadeIn()
        ) {
            Text(
                text = stringResource(R.string.users),
                style = AlgoismeTheme.typography.headerSmallMedium,
                color = AlgoismeTheme.colors.secondary
            )
        }
    }
}

@Composable
private fun NavigationBarRightContent(
    pickerOptions: Int,
    pickerPosition: Int,
    pickerCallback: (Int) -> Unit
) {
    var isDropDownMenuVisible by remember { mutableStateOf(false) }

    Box {
        Image(
            painter = painterResource(R.drawable.ic_filter_icon),
            contentDescription = null,
            modifier = Modifier.clickable { isDropDownMenuVisible = !isDropDownMenuVisible }
        )

        DropDownMenu(
            isDropDownVisible = isDropDownMenuVisible,
            options = stringArrayResource(pickerOptions),
            position = pickerPosition,
            onSelected = pickerCallback
        ) { isDropDownMenuVisible = !isDropDownMenuVisible }
    }
}

@Composable
private fun DropDownMenu(
    isDropDownVisible: Boolean,
    options: Array<String>,
    position: Int,
    onSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) = DropdownMenu(
    expanded = isDropDownVisible,
    onDismissRequest = onDismiss
) {
    options.forEachIndexed { index, label ->
        DropdownMenuItem(onClick = {
            onDismiss()
            onSelected(index)
        }) {
            Text(
                text = label,
                style = AlgoismeTheme.typography.primaryRegular,
                color = if (position == index) AlgoismeTheme.colors.secondary else AlgoismeTheme.colors.secondaryVariant
            )
        }
    }
}