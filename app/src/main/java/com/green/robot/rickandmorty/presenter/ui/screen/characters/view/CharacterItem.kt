package com.green.robot.rickandmorty.presenter.ui.screen.characters.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status
import com.green.robot.rickandmorty.presenter.extensions.getGenderString
import com.green.robot.rickandmorty.presenter.extensions.getStatusColor
import com.green.robot.rickandmorty.presenter.extensions.getStatusString

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    24.dp
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = character.image,
                contentDescription = character.name,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.empty),
                        contentDescription = "",
                    )
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp)
                    .align(Alignment.Center)
            )
            Row(
                modifier = Modifier
                    .align(
                        Alignment.BottomEnd
                    )
                    .clip(RoundedCornerShape(topStart = 24.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(start = 16.dp, top = 4.dp, end = 4.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            character.status.getStatusColor(),
                            CircleShape
                        )
                        .size(8.dp),
                )
                Text(
                    text = character.status.getStatusString(),
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .padding(top = 24.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = character.name,
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "${character.gender.getGenderString()} | ${character.species} ",
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CharactersScreenPreview() {
    MaterialTheme {
        CharacterItem(
            character = Character(
                id = 1,
                name = "Rick Sanchez",
                status = Status.ALIVE,
                species = "Human",
                gender = Gender.MALE,
                image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg"
            )
        )
    }
}