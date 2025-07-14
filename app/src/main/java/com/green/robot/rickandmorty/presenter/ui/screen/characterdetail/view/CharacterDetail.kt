package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.SubcomposeAsyncImage
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status
import com.green.robot.rickandmorty.presenter.extensions.getGenderString

@Composable
fun CharacterDetailView(
    modifier: Modifier = Modifier,
    characterDetail: CharacterDetail
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = characterDetail.image,
                contentDescription = characterDetail.name,
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Image(
                        painter = painterResource(R.drawable.empty),
                        contentDescription = "",
                    )
                },
                modifier = Modifier
                    .height(240.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 16.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Detail(
                    title = stringResource(R.string.gender),
                    param = characterDetail.gender.getGenderString(),
                    modifier = Modifier.fillMaxWidth()
                )
                Detail(
                    title = stringResource(R.string.species),
                    param = characterDetail.species,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun Detail(
    title: String,
    param: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 16.sp
        )
        Text(
            text = param,
            fontSize = 16.sp
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun CharacterDetailViewPreview() {
    MaterialTheme {
        CharacterDetailView(
            characterDetail = CharacterDetail(
                id = 1,
                name = "Rick Sanchez",
                status = Status.ALIVE,
                species = "Human",
                gender = Gender.MALE,
                image = "https://rickandmortyapi.com/api/character/avatar/361.jpeg",
                location = CharacterDetail.Location(
                    "1",
                    ""
                ),
                origin = CharacterDetail.Location(
                    "1",
                    ""
                )
            )
        )
    }
}

