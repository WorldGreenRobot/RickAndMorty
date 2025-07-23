package com.green.robot.rickandmorty.presenter.ui


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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
            val navController = rememberNavController()
            RickAndMortyTheme {
                NavHost(navController = navController, startDestination = Characters) {
                    composable<Characters> {
                        CharactersScreen(
                            navController
                        )
                    }

                    composable<CharacterDetail> {
                        val character: CharacterDetail = it.toRoute()
                        CharacterDetailScreen(
                            character.id,
                            character.characterName,
                            navController
                        )
                    }
                }
            }
        }
    }
}
