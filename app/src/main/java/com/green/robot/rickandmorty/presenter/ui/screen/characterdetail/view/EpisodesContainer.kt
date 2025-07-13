package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.domain.entity.episode.Episode

@Composable
fun EpisodesContainer(
    episodes: List<Episode>,
    modifier: Modifier = Modifier
) {

    val collapsedState = remember { mutableStateOf(StateEpisodesContainer.COLLAPSED) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        collapsedState.value = when (collapsedState.value) {
                            StateEpisodesContainer.COLLAPSED -> StateEpisodesContainer.EXPANDED
                            StateEpisodesContainer.EXPANDED -> StateEpisodesContainer.COLLAPSED
                        }
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.episodes, episodes.size),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )

                if (episodes.size > DEFAULT_SHOW_EPISODES) {
                    Icon(
                        imageVector = getIcon(collapsedState.value),
                        contentDescription = ""
                    )
                }
            }

            if (collapsedState.value == StateEpisodesContainer.EXPANDED || episodes.size <= DEFAULT_SHOW_EPISODES) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    episodes.fastForEach {
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

@Composable
@ReadOnlyComposable
private fun getIcon(state: StateEpisodesContainer): ImageVector {
    return when (state) {
        StateEpisodesContainer.EXPANDED -> Icons.Default.ArrowDropUp
        StateEpisodesContainer.COLLAPSED -> Icons.Default.ArrowDropDown
    }
}


enum class StateEpisodesContainer {
    COLLAPSED,
    EXPANDED
}

private const val DEFAULT_SHOW_EPISODES = 3

@Preview(showBackground = true)
@Composable
private fun EpisodesContainerPreview() {
    MaterialTheme {
        EpisodesContainer(
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
                )
            )
        )
    }
}
