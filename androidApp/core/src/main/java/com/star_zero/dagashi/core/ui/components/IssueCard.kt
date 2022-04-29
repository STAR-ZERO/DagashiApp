package com.star_zero.dagashi.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.flowlayout.FlowRow
import com.star_zero.dagashi.shared.model.Comment
import com.star_zero.dagashi.shared.model.Issue
import com.star_zero.dagashi.shared.model.Label

@Composable
fun IssueCard(
    issue: Issue,
    isFavorite: Boolean,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, Boolean) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = issue.title,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Favorite button
                IconButton(
                    onClick = { onClickFavorite(issue, isFavorite) },
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Top)
                ) {
                    // TODO: Fix color
                    val tint = if (isFavorite) {
                        Color.Yellow
                    } else {
                        Color.Gray
                    }
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = tint
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (issue.labels.isNotEmpty()) {
                IssueLabels(issue.labels)
                Spacer(modifier = Modifier.height(8.dp))
            }

            val linkColor = MaterialTheme.colors.primary

            val linkedText = remember(issue.body) { formatLinkedText(issue.body, linkColor) }

            ClickableText(
                text = linkedText,
                style = MaterialTheme.typography.caption.copy(color = LocalContentColor.current),
                onClick = { position ->
                    val annotation = linkedText.getStringAnnotations(
                        start = position,
                        end = position
                    ).firstOrNull()

                    annotation?.let {
                        onOpenLink(it.item)
                    }
                }
            )

            if (issue.comments.isNotEmpty()) {
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2F),
                    thickness = 1.dp,
                )
            }

            issue.comments.forEach { comment ->
                Comment(
                    comment = comment,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    onOpenLink(issue.url)
                }
            ) {
                Text(
                    text = "GITHUB",
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
private fun IssueLabels(labels: List<Label>) {
    FlowRow(
        mainAxisSpacing = 4.dp,
        crossAxisSpacing = 4.dp,
    ) {
        labels.forEach { label ->
            Box(
                Modifier.background(
                    color = Color(label.color),
                    shape = RoundedCornerShape(percent = 50)
                )
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = label.name,
                    style = MaterialTheme.typography.overline,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun Comment(comment: Comment, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {

            Image(
                painter = rememberImagePainter(
                    data = comment.author.avatarUrl,
                    builder = {
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = comment.author.name,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = comment.author.name,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        Text(
            text = comment.body,
            style = MaterialTheme.typography.caption
        )
    }
}
