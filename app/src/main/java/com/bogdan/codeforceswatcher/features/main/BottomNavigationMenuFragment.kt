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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

class NavigationMenuFragment: Fragment() {

    private val items = listOf(
        MenuItem(MenuItem.Tab.CONTESTS, R.drawable.ic_contests, R.drawable.ic_contests_gradient),
        MenuItem(MenuItem.Tab.USERS, R.drawable.ic_users, R.drawable.ic_users_gradient),
        MenuItem(MenuItem.Tab.NEWS, R.drawable.ic_news, R.drawable.ic_news_gradient),
        MenuItem(MenuItem.Tab.PROBLEMS, R.drawable.ic_problems, R.drawable.ic_problems_gradient),
    )
    private var selectedTab: MutableState<MenuItem.Tab> = mutableStateOf(MenuItem.Tab.CONTESTS)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                NavigationMenuView(
                    items = items,
                    selectedTab = selectedTab,
                    onMenuItemTap = {
                        selectedTab.value = it
                        (activity as OnMenuItemTapListener).onMenuItemTap(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun NavigationMenuView(
    items: List<MenuItem>,
    selectedTab: State<MenuItem.Tab>,
    onMenuItemTap: (MenuItem.Tab) -> Unit,
    modifier: Modifier = Modifier
) = Row(
    modifier = modifier
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
        colorFilter = if (item.tab == selectedTab) null else ColorFilter.tint(AlgoismeTheme.colors.menuItem)
    )

    Text(
        text = stringResource(item.tab.title),
        style = AlgoismeTheme.typography.hintRegular.copy(fontSize = 11.sp),
        color = AlgoismeTheme.colors.menuItem
    )
}

private val MenuItem.Tab.title get() = when(this) {
    MenuItem.Tab.CONTESTS -> R.string.contests
    MenuItem.Tab.USERS -> R.string.users
    MenuItem.Tab.NEWS -> R.string.news
    MenuItem.Tab.PROBLEMS -> R.string.problems
}