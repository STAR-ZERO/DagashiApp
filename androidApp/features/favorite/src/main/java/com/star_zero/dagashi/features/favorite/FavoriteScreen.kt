package com.star_zero.dagashi.features.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.star_zero.dagashi.core.CoreString
import com.star_zero.dagashi.core.ui.components.IssueCard
import com.star_zero.dagashi.core.usecase.OpenLinkUseCase
import com.star_zero.dagashi.shared.model.Issue

@Destination
@Composable
fun FavoriteScreen() {
    val viewModel: FavoriteViewModel = hiltViewModel()

    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val openLinkUseCase = remember(uriHandler, context) {
        OpenLinkUseCase(context, uriHandler)
    }

    LaunchedEffect(Unit) {
        viewModel.getFavorites()
    }

    val uiState by viewModel.uiState.collectAsState(FavoriteUiState())

    FavoriteContainer(
        uiState = uiState,
        onOpenLink = {
            openLinkUseCase(it, uiState.isOpenLinkInApp)
        },
        onClickFavorite = viewModel::toggleFavorite,
        onDeleteAll = viewModel::deleteAllFavorites,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoriteContainer(
    uiState: FavoriteUiState,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, String, Boolean) -> Unit,
    onDeleteAll: () -> Unit,
) {
    Surface {
        Scaffold(
            topBar = {
                AppBar(
                    onDeleteAll = onDeleteAll,
                )
            }
        ) { innerPadding ->
            FavoriteContent(
                uiState = uiState,
                onOpenLink = onOpenLink,
                onClickFavorite = onClickFavorite,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun AppBar(
    onDeleteAll: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.favorite_title))
        },
        actions = {
            DeleteAllButton(
                onDeleteAll = onDeleteAll
            )
        },
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
        )
    )
}

@Composable
private fun DeleteAllButton(
    onDeleteAll: () -> Unit,
) {

    var isShowConfirmation by remember { mutableStateOf(false) }
    if (isShowConfirmation) {
        AlertDialog(
            onDismissRequest = { isShowConfirmation = false },
            title = {
                Text(
                    text = stringResource(id = R.string.favorite_delete_all)
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onDeleteAll()
                    isShowConfirmation = false
                }) {
                    Text(text = stringResource(id = CoreString.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isShowConfirmation = false
                }) {
                    Text(text = stringResource(id = CoreString.cancel))
                }
            }
        )
    }

    IconButton(onClick = { isShowConfirmation = true }) {
        Icon(Icons.Filled.Delete, "Delete")
    }
}

@Composable
private fun FavoriteContent(
    uiState: FavoriteUiState,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (uiState.favorites.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.favorite_no_data),
                style = MaterialTheme.typography.titleMedium
            )
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(320.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            items(uiState.favorites, key = { it.issue.url }) { favoriteItem ->
                IssueCard(
                    issue = favoriteItem.issue,
                    isFavorite = favoriteItem.isFavorite,
                    onOpenLink = onOpenLink,
                    onClickFavorite = { issue, isFavorite ->
                        onClickFavorite(
                            issue,
                            favoriteItem.milestoneId,
                            isFavorite
                        )
                    }
                )
            }
        }
    }
}
