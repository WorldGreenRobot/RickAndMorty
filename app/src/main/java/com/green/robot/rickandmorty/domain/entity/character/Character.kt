package com.green.robot.rickandmorty.domain.entity.character

import androidx.compose.runtime.Immutable


@Immutable
data class Character(
    val id: Int? = null,
    val name: String? = null,
    val status: Status? = null,
    val species: String? = null,
    val gender: Gender? = null,
    val image: String? = null
)
