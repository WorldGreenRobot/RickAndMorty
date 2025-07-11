package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.green.robot.rickandmorty.domain.entity.episode.Episode

@Composable
fun EpisodeItem(
    episode: Episode,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = "${episode.episode}: ${episode.name}"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Air Date: "
                )
                Text(text = episode.airDate)
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun EpisodeItemPreview() {
    MaterialTheme {
        EpisodeItem(
            episode = Episode(
                id = 1,
                name = "Pilot",
                airDate = "December 2, 2013",
                episode = "S01E01"
            )
        )
    }
}