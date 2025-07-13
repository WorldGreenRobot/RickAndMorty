package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetailData
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status
import com.green.robot.rickandmorty.domain.entity.episode.Episode
import com.green.robot.rickandmorty.domain.entity.location.Location
import com.green.robot.rickandmorty.presenter.extensions.getStatusString
import com.green.robot.rickandmorty.presenter.ui.components.EmptyList
import com.green.robot.rickandmorty.presenter.ui.components.LoadingView
import com.green.robot.rickandmorty.presenter.ui.components.Screen
import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.view.CharacterDetailView
import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.view.EpisodeItem
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CharacterDetailScreen(
    id: Int,
    characterName: String,
    navigateController: NavController,
    viewModel: CharacterDetailViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData(id)
    }

    CharacterContent(
        state = state,
        name = characterName,
        onAction = {
            actionHandler(it, navigateController, viewModel)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterContent(
    state: CharacterDetailState,
    name: String,
    onAction: (CharacterDetailAction) -> Unit
) {

    val status = state.data?.characterDetail?.status?.getStatusString()
    Screen(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = state.showRefresh,
        onRefresh = {
            onAction(CharacterDetailAction.OnRefresh)
        },
        uiError = state.error,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = "$name ${if (state.data != null || !state.showLoading) "($status)" else ""}"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onAction(CharacterDetailAction.OnBackPressed)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                state.showLoading -> {
                    item {
                        LoadingView(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                state.data == null && !state.showLoading -> {
                    item {
                        EmptyList(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                else -> {
                    state.data?.characterDetail?.let {
                        item {
                            CharacterDetailView(
                                characterDetail = it,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            text = stringResource(R.string.episodes),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    items(state.data?.episodes.orEmpty(), key = { it.id }) {
                        EpisodeItem(
                            episode = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun actionHandler(
    action: CharacterDetailAction,
    navigateController: NavController,
    viewModel: CharacterDetailViewModel
) {
    when (action) {
        is CharacterDetailAction.OnBackPressed -> {
            navigateController.popBackStack()
        }

        is CharacterDetailAction.OnRefresh -> {
            viewModel.refresh()
        }
    }
}

sealed interface CharacterDetailAction {
    object OnBackPressed : CharacterDetailAction

    object OnRefresh : CharacterDetailAction
}

@Composable
@Preview
private fun CharacterDetailScreenPreview() {
    MaterialTheme {
        CharacterContent(
            state = CharacterDetailState(
                showLoading = false,
                data = CharacterDetailData(
                    characterDetail = CharacterDetail(
                        id = 1,
                        name = "Rick Sanchez",
                        status = Status.ALIVE,
                        species = "Human",
                        gender = Gender.MALE,
                        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg",
                        origin = "Earth"
                    ),
                    episodes = listOf(
                        Episode(
                            id = 1,
                            name = "Pilot",
                            airDate = "December 2, 2013",
                            episode = "S01E01"
                        ),
                        Episode(
                            id = 2,
                            name = "Pilot",
                            airDate = "December 2, 2013",
                            episode = "S01E01"
                        ),
                        Episode(
                            id = 3,
                            name = "Pilot",
                            airDate = "December 2, 2013",
                            episode = "S01E01"
                        ),
                    ),
                    location = Location(
                        id = 1,
                        name = "Earth",
                        type = "Planet",
                        dimension = "Dimension C-137"
                    )
                )
            ),
            name = "Rick Sanchez",
            onAction = {}
        )
    }
}