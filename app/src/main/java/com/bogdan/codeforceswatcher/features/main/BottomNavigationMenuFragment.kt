package com.bogdan.codeforceswatcher.features.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

class NavigationMenuFragment(
    private val items: List<MenuItem>,
    private val selectedTab: State<MenuItem.Tab>,
    private val onMenuItemTap: (MenuItem.Tab) -> Unit
): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                NavigationMenuView(items, selectedTab, onMenuItemTap)
            }
        }
    }
}

@Composable
private fun NavigationMenuView(
    items: List<MenuItem>,
    selectedTab: State<MenuItem.Tab>,
    onMenuItemTap: (MenuItem.Tab) -> Unit
) = Row(
    modifier = Modifier
        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        .background(AlgoismeTheme.colors.primaryVariant)
        .padding(horizontal = 14.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    items.subList(0, 2).forEach {
        NavigationMenuItemView(it, selectedTab.value, onMenuItemTap, Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.fillMaxWidth(0.15f))

    items.subList(2, 4).forEach {
        NavigationMenuItemView(it, selectedTab.value, onMenuItemTap, Modifier.weight(1f))
    }
}

@Composable
private fun NavigationMenuItemView(
    item: MenuItem,
    selectedTab: MenuItem.Tab,
    onMenuItemTap: (MenuItem.Tab) -> Unit,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier.clickable { onMenuItemTap(item.tab) },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(2.dp)
) {
    Image(
        painter = painterResource(if (item.tab == selectedTab) item.selectedIconId else item.iconId),
        contentDescription = null,
        colorFilter = if (item.tab == selectedTab) null else ColorFilter.tint(AlgoismeTheme.colors.secondaryVariant)
    )

    Text(
        text = stringResource(tabTitle(item.tab)),
        style = AlgoismeTheme.typography.hintRegular.copy(fontSize = 11.sp),
        color = AlgoismeTheme.colors.secondaryVariant
    )
}

private fun tabTitle(tab: MenuItem.Tab) = when(tab) {
    MenuItem.Tab.CONTESTS -> R.string.contests
    MenuItem.Tab.USERS -> R.string.users
    MenuItem.Tab.NEWS -> R.string.news
    MenuItem.Tab.PROBLEMS -> R.string.problems
}