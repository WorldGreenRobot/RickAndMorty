package com.green.robot.rickandmorty.domain.entity.character

import androidx.compose.runtime.Immutable


@Immutable
data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val gender: Gender,
    val image: String
)
