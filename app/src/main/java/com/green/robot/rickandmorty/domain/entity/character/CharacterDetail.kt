package com.green.robot.rickandmorty.domain.entity.character

import androidx.compose.runtime.Immutable

@Immutable
data class CharacterDetail(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val image: String,
    val episodes: List<String> = emptyList()
) {
    data class Location(
        val id: String?,
        val name: String
    )
}