package com.bogdan.codeforceswatcher.features.problems

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.R

@Composable
fun ProblemsView(
    problemsState: State<List<ProblemUIModel>>,
    onProblem: (String, String) -> Unit
) {
    Box {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primaryVariant)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colors.primary)
        ) {
            items(problemsState.value) { problem ->
                ProblemView(
                    problem = problem,
                    onProblem = onProblem
                )
            }
        }
    }
}

@Composable
private fun ProblemView(
    problem: ProblemUIModel,
    onProblem: (String, String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.clickable { onProblem(problem.link, problem.title) }
        ) {
            Text(
                text = problem.title,
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = problem.subtitle,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.secondaryVariant,
                modifier = Modifier.height(16.dp)
            )
        }

        Icon(
            painter = painterResource(R.drawable.ic_star),
            contentDescription = null,
            tint = colorResource(if (problem.isFavourite) R.color.colorAccent else R.color.black),
            modifier = Modifier
                .size(24.dp)
        )
    }
}

data class ProblemUIModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val link: String,
    val isFavourite: Boolean
)