package com.green.robot.rickandmorty.domain.entity.character

import androidx.compose.runtime.Immutable

@Immutable
data class CharacterDetail(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val image: String,
    val episodes: List<String> = emptyList()
)