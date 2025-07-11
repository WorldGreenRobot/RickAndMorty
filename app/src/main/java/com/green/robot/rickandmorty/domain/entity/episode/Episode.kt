package com.green.robot.rickandmorty.domain.entity.episode

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
)
