package com.green.robot.rickandmorty.domain.entity.character

import androidx.compose.runtime.Immutable

@Immutable
data class CharacterDetail(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val originId: String,
    val originName: String,
    val image: String,
    val episodes: List<String> = emptyList()
)