package com.bogdan.codeforceswatcher.features.contests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

class ContestsFragmentNew : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            AlgoismeTheme {
                ContentView()
            }
        }
    }
}

@Composable
private fun ContentView() = LazyColumn(
    modifier = Modifier
        .background(AlgoismeTheme.colors.primary)
        .padding(horizontal = 20.dp)
) {
    items(10) {
        ContestView()
    }
}

@Composable
private fun ContestView() = Row(
    modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp)
        .height(36.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    Image(
        painter = painterResource(R.drawable.ic_codeforces),
        contentDescription = null
    )

    Spacer(modifier = Modifier.width(8.dp))

    Column {
        Text(
            text = "Codeforces Round #745 (Div. 1)",
            style = AlgoismeTheme.typography.primarySemiBold,
            color = AlgoismeTheme.colors.secondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = "13:05 Sep 30, Thursday",
            style = AlgoismeTheme.typography.hintRegular,
            color = AlgoismeTheme.colors.secondaryVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.offset(y = (-4).dp)
        )
    }

    Spacer(modifier = Modifier.weight(1f))

    Image(
        painter = painterResource(R.drawable.ic_calendar),
        contentDescription = null
    )
}

@Composable
@Preview
private fun Preview() = ContentView()