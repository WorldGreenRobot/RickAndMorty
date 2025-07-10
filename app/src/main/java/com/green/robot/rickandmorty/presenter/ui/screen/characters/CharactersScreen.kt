package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.green.robot.rickandmorty.data.entity.Character
import com.green.robot.rickandmorty.presenter.navigation.CharacterDetail
import com.green.robot.rickandmorty.presenter.navigation.Characters
import com.green.robot.rickandmorty.presenter.ui.components.Screen
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
            handleAction(it, navigateController)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharactersContent(
    state: CharactersState,
    onAction: (CharactersAction) -> Unit = {}
) {
    Screen(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = "Characters") }
            )
        }
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.data.orEmpty(), key = { it.id }) {
                CharacterItem(
                    character = it,
                    modifier = Modifier
                        .clickable {
                            onAction(CharactersAction.OpenCharacterDetail(it.id))
                        }
                )
            }
        }
    }
}

private fun handleAction(action: CharactersAction, navController: NavController) {
    when (action) {
        is CharactersAction.OpenCharacterDetail -> {
            navController.navigate(
                CharacterDetail(
                    id = action.id
                )
            )
        }
    }
}

sealed interface CharactersAction {
    data class OpenCharacterDetail(val id: Int) : CharactersAction
}

@Composable
@Preview
private fun CharactersScreenPreview() {
    MaterialTheme {
        CharactersContent(
            state = CharactersState(
                showLoading = false,
                data = listOf(
                    Character(
                        id = 1,
                        name = "Rick Sanchez",
                        status = "Alive",
                        species = "Human",
                        gender = "Male",
                        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg"
                    ),
                    Character(
                        id = 2,
                        name = "Morty Smith",
                        status = "Alive",
                        species = "Human",
                        gender = "Male",
                        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg"
                    ),
                    Character(
                        id = 3,
                        name = "Summer Smith",
                        status = "Alive",
                        species = "Human",
                        gender = "Female",
                        image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg"
                    )
                ),
                error = null
            )
        )
    }
}