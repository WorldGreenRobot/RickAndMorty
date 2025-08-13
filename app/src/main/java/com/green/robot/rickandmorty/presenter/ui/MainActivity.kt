package com.green.robot.rickandmorty.presenter.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.green.robot.rickandmorty.presenter.navigation.AppRoute
import com.green.robot.rickandmorty.presenter.navigation.CharacterDetail
import com.green.robot.rickandmorty.presenter.navigation.Characters
import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.CharacterDetailScreen
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersScreen
import com.green.robot.rickandmorty.presenter.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack: SnapshotStateList<AppRoute> = remember { mutableStateListOf(Characters) }
            RickAndMortyTheme {
                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        entry<Characters> {
                            CharactersScreen(
                                backStack
                            )
                        }
                        entry<CharacterDetail> {
                            CharacterDetailScreen(
                                it.id,
                                it.characterName,
                                backStack
                            )
                        }
                    }
                )
            }
        }
    }
}
