package com.green.robot.rickandmorty.domain.entity.character

import androidx.compose.runtime.Immutable
import com.green.robot.rickandmorty.domain.entity.episode.Episode

@Immutable
data class CharacterDetailData(
    val characterDetail: CharacterDetail,
    val episodes: List<Episode>
)