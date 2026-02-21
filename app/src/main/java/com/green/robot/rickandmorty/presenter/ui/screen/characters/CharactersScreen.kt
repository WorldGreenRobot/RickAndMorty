package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status
import com.green.robot.rickandmorty.presenter.navigation.CharacterDetail
import com.green.robot.rickandmorty.presenter.navigation.Navigator
import com.green.robot.rickandmorty.presenter.ui.components.EmptyList
import com.green.robot.rickandmorty.presenter.ui.components.LoadingView
import com.green.robot.rickandmorty.presenter.ui.components.Screen
import com.green.robot.rickandmorty.presenter.ui.components.SearchableTopAppBar
import com.green.robot.rickandmorty.presenter.ui.dialogs.FilterBottomSheetDialog
import com.green.robot.rickandmorty.presenter.ui.screen.characters.view.CharacterItem
import kotlinx.coroutines.flow.flowOf
import org.orbitmvi.orbit.compose.collectAsState

@Composable
fun CharactersScreen(
    navigator: Navigator,
    viewModel: CharactersViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()

    val characterItems = state.data?.collectAsLazyPagingItems()
    val loadState = characterItems?.loadState

    CharactersContent(
        state = state,
        loadState = loadState?.mediator,
        characterItems = characterItems,
        onAction = {
            handleAction(it, navigator, viewModel, characterItems)
        }
    )

    Dialogs(state.dialogs, viewModel, characterItems)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersContent(
    state: CharactersState,
    loadState: LoadStates? = null,
    characterItems: LazyPagingItems<Character>? = null,
    onAction: (CharactersAction) -> Unit = {}
) {
    val isShowSearch = remember { mutableStateOf(false) }

    val error = remember(loadState) {
        mutableStateOf(
            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                if (loadState.append is LoadState.Error)
                    (loadState.append as LoadState.Error).error
                else
                    (loadState.refresh as LoadState.Error).error
            } else null
        )
    }

    Screen(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = loadState?.append is LoadState.Loading || loadState?.refresh is LoadState.Loading || loadState?.prepend is LoadState.Loading,
        uiError = error.value?.message,
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
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = stringResource(R.string.characters),
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (!state.search.isNullOrEmpty()) {
                                Text(
                                    text = stringResource(R.string.search),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 12.sp
                                )

                                Text(
                                    text = state.search,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 12.sp
                                )
                            }

                            if (!state.filterData?.filters.isNullOrEmpty()) {
                                Text(
                                    text = stringResource(R.string.filters),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 12.sp
                                )
                                state.filterData.filters.fastForEach {
                                    Text(
                                        it.value,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
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
            loadState?.append?.endOfPaginationReached == true && (characterItems?.itemCount
                ?: 0) < 1 -> {
                EmptyList(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    text = stringResource(R.string.characters_not_found)
                )
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(characterItems?.itemCount ?: 0) {

                        val item = characterItems?.get(it)
                        item?.let {
                            CharacterItem(
                                character = it,
                                modifier = Modifier
                                    .then(if (it.id == null || it.name == null) Modifier else Modifier.clickable {
                                        onAction(
                                            CharactersAction.OpenCharacterDetail(
                                                it.id,
                                                it.name
                                            )
                                        )
                                    })
                            )
                        }
                    }
                    item {
                        if (loadState?.append == LoadState.Loading) {
                            LoadingView(
                                modifier = Modifier.fillMaxSize(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Dialogs(
    dialogs: List<CharactersDialog>,
    viewModel: CharactersViewModel,
    characterItems: LazyPagingItems<Character>?
) {
    dialogs.fastForEach {
        when (it) {
            is CharactersDialog.FilterCharacters -> {
                FilterBottomSheetDialog(
                    onDismissRequest = {
                        viewModel.closeFilterDialog(it)
                    },
                    onPositiveButtonClick = { status, gender, species ->
                        viewModel.setFilter(status, gender, species)
                        characterItems?.refresh()
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
    navigator: Navigator,
    viewModel: CharactersViewModel,
    characterItems: LazyPagingItems<Character>?
) {
    when (action) {
        is CharactersAction.OpenCharacterDetail -> {
            navigator.navigate(
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
            viewModel.requestSearch()
            characterItems?.refresh()
        }

        is CharactersAction.Refresh -> {
            characterItems?.refresh()
        }

        is CharactersAction.OpenFilterDialog -> {
            viewModel.openFilterDialog()
        }
    }
}

sealed interface CharactersAction {
    data class OpenCharacterDetail(val id: Int, val characterName: String) : CharactersAction
    data class UpdateSearchQuery(val query: String) : CharactersAction
    object SearchCharacter : CharactersAction
    object Refresh : CharactersAction
    object OpenFilterDialog : CharactersAction
}


@Preview
@Composable
private fun CharactersScreenEmptyPreview() {
    MaterialTheme {
        CharactersContent(
            state = CharactersState(
                data = flowOf(
                    PagingData.from(
                        emptyList()
                    )
                )
            )
        )
    }
}

@Composable
@Preview(name = "Characters Success")
private fun CharactersScreenPreview() {
    MaterialTheme {
        CharactersContent(
            state = CharactersState(
                data = flowOf(
                    PagingData.from(
                        listOf(
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
                        )
                    )
                )
            )
        )
    }
}