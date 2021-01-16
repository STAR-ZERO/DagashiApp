package com.star_zero.dagashi.milestone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.star_zero.dagashi.shared.DagashiSDK
import com.star_zero.dagashi.shared.db.DatabaseDriverFactory
import com.star_zero.dagashi.shared.network.DagashiAPI
import com.star_zero.dagashi.shared.model.Milestone

val dagashiSDK = DagashiSDK(DatabaseDriverFactory())

@Composable
fun MilestoneScreen() {
    var milestones: List<Milestone> by remember { mutableStateOf(listOf()) }

    LaunchedTask {
        // TODO: Use cache
        milestones = dagashiSDK.getMilestone(true)
    }

    Surface(color = MaterialTheme.colors.background) {
        LazyColumnFor(
            items = milestones,
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) { milestone ->
            MilestoneCard(milestone)
        }
    }
}

@Composable
private fun MilestoneCard(milestone: Milestone) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = {
                // TODO
            })
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.high) {
                Text(
                    text = milestone.title,
                    style = MaterialTheme.typography.subtitle1,
                )
            }
            Spacer(modifier = Modifier.preferredHeight(8.dp))
            ProvideEmphasis(emphasis = AmbientEmphasisLevels.current.medium) {
                Text(
                    text = milestone.body,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}
