package com.green.robot.rickandmorty.presenter.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.green.robot.rickandmorty.di.DaggerViewModelFactory
import com.green.robot.rickandmorty.getAppComponent
import com.green.robot.rickandmorty.presenter.navigation.CharacterDetail
import com.green.robot.rickandmorty.presenter.navigation.Characters
import com.green.robot.rickandmorty.presenter.navigation.Navigator
import com.green.robot.rickandmorty.presenter.navigation.rememberNavigationState
import com.green.robot.rickandmorty.presenter.navigation.toEntries
import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.CharacterDetailScreen
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersScreen
import com.green.robot.rickandmorty.presenter.ui.theme.RickAndMortyTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this)
        enableEdgeToEdge()
        setContent {
            val navigationState = rememberNavigationState(
                startRoute = Characters,
                topLevelRoutes = setOf(Characters)
            )

            val navigator = remember { Navigator(navigationState) }

            val entryProvider = entryProvider {
                entry<Characters> { _ ->
                    CharactersScreen(
                        navigator = navigator,
                        viewModelFactory = viewModelFactory,
                    )
                }
                entry<CharacterDetail> { key ->
                    CharacterDetailScreen(
                        id = key.id,
                        characterName = key.characterName,
                        navigator = navigator,
                        viewModelFactory = viewModelFactory,
                    )
                }
            }
            RickAndMortyTheme {
                NavDisplay(
                    entries = navigationState.toEntries(entryProvider),
                    onBack = { navigator.goBack() },
                    sceneStrategy = remember { DialogSceneStrategy() }
                )
            }
        }
    }
}
