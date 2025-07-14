package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.sp
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
import com.green.robot.rickandmorty.presenter.ui.components.Screen
import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.view.CharacterDetailView
import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.view.EpisodesContainer
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
    Screen(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = state.showLoading,
        onRefresh = {
            onAction(CharacterDetailAction.OnRefresh)
        },
        uiError = state.error,
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = name,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        state.data?.characterDetail?.status?.let {
                            Text(
                                text = it.getStatusString(),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 12.sp
                            )
                        }
                    }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
                .verticalScroll(rememberScrollState()),
        ) {
            when {
                state.data == null && !state.showLoading -> {
                    EmptyList(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        text = stringResource(R.string.detail_not_loading)
                    )
                }

                state.data != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CharacterDetailView(
                            characterDetail = state.data.characterDetail,
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        LocationView(
                            modifier = Modifier.fillMaxWidth(),
                            location = state.data.origin,
                            title = stringResource(R.string.origin)
                        )

                        LocationView(
                            modifier = Modifier.fillMaxWidth(),
                            location = state.data.location,
                            title = stringResource(R.string.location)
                        )

                        EpisodesContainer(
                            episodes = state.data.episodes,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationView(
    modifier: Modifier = Modifier,
    location: Location?,
    title: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
            if (location != null) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${location.type}  ${location.name}"
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (location.dimension.equals(stringResource(R.string.unknown), true))
                        stringResource(R.string.dimensions, location.dimension)
                    else
                        location.dimension
                )
            } else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.unknown)
                )
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
@Preview(name = "Detail is Empty")
private fun CharacterDetailScreenEmptyPreview() {
    MaterialTheme {
        CharacterContent(
            state = CharacterDetailState(
                showLoading = false,
                data = null
            ),
            name = "Rick Sanchez",
            onAction = {}
        )
    }
}

@Composable
@Preview(name = "Detail is Success")
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
                        origin = CharacterDetail.Location(
                            id = "1",
                            name = "Earth"
                        ),
                        location = CharacterDetail.Location(
                            id = "1",
                            name = "Earth"
                        )
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
                    ),
                    origin = Location(
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