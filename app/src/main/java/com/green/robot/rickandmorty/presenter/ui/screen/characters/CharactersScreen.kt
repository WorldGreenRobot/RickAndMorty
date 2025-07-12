package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavController
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status
import com.green.robot.rickandmorty.presenter.navigation.CharacterDetail
import com.green.robot.rickandmorty.presenter.ui.components.EmptyList
import com.green.robot.rickandmorty.presenter.ui.components.LoadingView
import com.green.robot.rickandmorty.presenter.ui.components.Screen
import com.green.robot.rickandmorty.presenter.ui.components.SearchableTopAppBar
import com.green.robot.rickandmorty.presenter.ui.components.chip.FilterInputChip
import com.green.robot.rickandmorty.presenter.ui.dialogs.FilterBottomSheetDialog
import com.green.robot.rickandmorty.presenter.ui.screen.characters.view.CharacterItem
import org.koin.compose.viewmodel.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CharactersScreen(
    navigateController: NavController,
    viewModel: CharactersViewModel = koinViewModel()
) {
    val state by viewModel.collectAsState()

    CharactersContent(
        state = state,
        onAction = {
            handleAction(it, navigateController, viewModel)
        }
    )

    Dialogs(state.dialogs, viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersContent(
    state: CharactersState,
    onAction: (CharactersAction) -> Unit = {}
) {
    val isShowSearch = remember { mutableStateOf(false) }

    Screen(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = state.showRefreshLoading,
        uiError = state.error,
        onRefresh = {
            onAction(CharactersAction.Refresh)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(CharactersAction.OpenFilterDialog)
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = ""
                )
            }
        },
        topBar = {
            SearchableTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.characters),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                currentSearchQuery = state.search.orEmpty(),
                onSearchQueryChange = {
                    onAction(CharactersAction.UpdateSearchQuery(it))
                },
                onSearchExecute = {
                    onAction(CharactersAction.SearchCharacter)
                },
                isSearchActive = isShowSearch.value,
                onSearchActiveChange = {
                    isShowSearch.value = it
                },
                onCloseSearch = {
                    isShowSearch.value = false
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) {
        when {
            state.showFirstLoading -> {
                LoadingView(
                    modifier = Modifier.fillMaxSize(),
                )
            }

            state.data.isNullOrEmpty() -> {
                EmptyList(
                    modifier = Modifier
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top= 16.dp)
                ) {
                    if (!state.filterData?.filters.isNullOrEmpty()) {
                        Text(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            text = stringResource(R.string.filters),
                            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
                            fontWeight = MaterialTheme.typography.titleMedium.fontWeight,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize
                        )
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.filterData.filters) {
                                FilterInputChip(
                                    text = it.value,
                                    onDismiss = {
                                        onAction(CharactersAction.RemoveFilter(it.type))
                                    }
                                )
                            }
                        }
                    }

                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.data, key = { it.id }) {
                            CharacterItem(
                                character = it,
                                modifier = Modifier
                                    .clickable {
                                        onAction(
                                            CharactersAction.OpenCharacterDetail(
                                                it.id,
                                                it.name
                                            )
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Dialogs(dialogs: List<CharactersDialog>, viewModel: CharactersViewModel) {
    dialogs.fastForEach {
        when (it) {
            is CharactersDialog.FilterCharacters -> {
                FilterBottomSheetDialog(
                    onDismissRequest = {
                        viewModel.closeFilterDialog(it)
                    },
                    onPositiveButtonClick = { status, gender, species ->
                        viewModel.setFilter(status, gender, species)
                    },
                    filterCharacters = it.filterData,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

private fun handleAction(
    action: CharactersAction,
    navController: NavController,
    viewModel: CharactersViewModel
) {
    when (action) {
        is CharactersAction.OpenCharacterDetail -> {
            navController.navigate(
                CharacterDetail(
                    id = action.id,
                    characterName = action.characterName
                )
            )
        }

        is CharactersAction.UpdateSearchQuery -> {
            viewModel.updateSearch(action.query)
        }

        is CharactersAction.SearchCharacter -> {
            viewModel.searchCharacter()
        }

        is CharactersAction.Refresh -> {
            viewModel.refresh()
        }

        is CharactersAction.OpenFilterDialog -> {
            viewModel.openFilterDialog()
        }

        is CharactersAction.RemoveFilter -> {
            viewModel.removeFilter(action.filterType)
        }
    }
}

sealed interface CharactersAction {
    data class OpenCharacterDetail(val id: Int, val characterName: String) : CharactersAction
    data class UpdateSearchQuery(val query: String) : CharactersAction
    object SearchCharacter : CharactersAction
    object Refresh : CharactersAction
    object OpenFilterDialog : CharactersAction
    data class RemoveFilter(val filterType: CharactersState.FilterData.FilterType) : CharactersAction
}

@Composable
@Preview
private fun CharactersScreenPreview() {
    MaterialTheme {
        CharactersContent(
            state = CharactersState(
                showRefreshLoading = false,
                showFirstLoading = false,
                data = listOf(
                    Character(
                        id = 1,
                        name = "Rick Sanchez",
                        status = Status.ALIVE,
                        species = "Human",
                        gender = Gender.MALE,
                        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg"
                    ),
                    Character(
                        id = 2,
                        name = "Morty Smith",
                        status = Status.ALIVE,
                        species = "Human",
                        gender = Gender.MALE,
                        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg"
                    ),
                    Character(
                        id = 3,
                        name = "Summer Smith",
                        status = Status.ALIVE,
                        species = "Human",
                        gender = Gender.MALE,
                        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg"
                    )
                ),
                error = null
            )
        )
    }
}