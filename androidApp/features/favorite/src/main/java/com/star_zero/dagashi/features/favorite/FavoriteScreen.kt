package com.star_zero.dagashi.features.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
fun FavoriteScreen(
    navigator: FavoriteNavigator
) {
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
        onClickBack = navigator::navigateBack
    )
}

@Composable
private fun FavoriteContainer(
    uiState: FavoriteUiState,
    onOpenLink: (String) -> Unit,
    onClickFavorite: (Issue, String, Boolean) -> Unit,
    onDeleteAll: () -> Unit,
    onClickBack: () -> Unit
) {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(
            topBar = {
                AppBar(
                    onDeleteAll = onDeleteAll,
                    onClickBack = onClickBack
                )
            }
        ) {
            FavoriteContent(
                uiState = uiState,
                onOpenLink = onOpenLink,
                onClickFavorite = onClickFavorite
            )
        }
    }
}

@Composable
private fun AppBar(
    onDeleteAll: () -> Unit,
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.favorite_title))
        },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(Icons.Filled.ArrowBack, "Back")
            }
        },
        actions = {
            DeleteAllButton(
                onDeleteAll = onDeleteAll
            )
        }
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
) {
    if (uiState.favorites.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.favorite_no_data),
                style = MaterialTheme.typography.h6
            )
        }
    } else {
        LazyColumn(contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)) {
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
