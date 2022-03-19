package com.bogdan.codeforceswatcher.features.contests

import androidx.annotation.StringRes
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import com.bogdan.codeforceswatcher.features.filters.models.FilterItem
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.tabs.TabLayout

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContestsTopBar(
    filters: List<FilterItem>,
    modifier: Modifier = Modifier
) {
    var isFiltersVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(horizontal = 25.dp)) {
        NavigationBar(isFiltersVisible) { isFiltersVisible = !isFiltersVisible }

        AnimatedVisibility(
            visible = isFiltersVisible,
            enter = expandVertically(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ),
            exit = shrinkVertically(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        ) {
            FiltersView(filters)
        }
    }
}

@Composable
private fun NavigationBar(
    isFiltersVisible: Boolean,
    onFilter: () -> Unit
) = Row(
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Box {
        NavigationBarTitle(R.string.contests, !isFiltersVisible)
        NavigationBarTitle(R.string.filters, isFiltersVisible)
    }

    Image(
        painter = painterResource(if (isFiltersVisible) R.drawable.ic_cross_icon else R.drawable.ic_filter_icon),
        contentDescription = null,
        modifier = Modifier.clickable { onFilter() }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NavigationBarTitle(
    @StringRes id: Int,
    isVisible: Boolean
) = AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn(),
    exit = fadeOut()
) {
    Text(
        text = stringResource(id),
        style = AlgoismeTheme.typography.headerSmallMedium,
        color = AlgoismeTheme.colors.secondary
    )
}

@Composable
private fun FiltersView(
    filterItems: List<FilterItem>
) = Column(
    modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
) {
    filterItems.chunked(5).forEach {
        FiltersRowView(it)
    }
}

@Composable
private fun FiltersRowView(
    items: List<FilterItem>
) = Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
) {
    items.forEach {
        with(it) {
            FilterView(imageId, isChecked, onSwitchTap)
        }
    }
}

@Composable
private fun FilterView(
    imageId: Int?,
    isChecked: Boolean,
    onClick: (Boolean) -> Unit
) {
    val grayscaleMatrix = ColorMatrix().apply { setToSaturation(0f) }

    Image(
        painter = painterResource(imageId!!),
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .clickable { onClick(!isChecked) },
        alpha = if (!isChecked) 0.5f else 1f,
        colorFilter = ColorFilter.colorMatrix(if (!isChecked) grayscaleMatrix else ColorMatrix())
    )
}