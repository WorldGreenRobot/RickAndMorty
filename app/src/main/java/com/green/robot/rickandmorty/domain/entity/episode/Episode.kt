package com.green.robot.rickandmorty.domain.entity.episode

import androidx.compose.runtime.Immutable

@Immutable
data class Episode(
    val id: Int,
    val name: String,
    val airDate: String,
    val episode: String,
)
