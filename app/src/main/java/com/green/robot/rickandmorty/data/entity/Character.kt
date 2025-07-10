package com.green.robot.rickandmorty.data.entity

import androidx.compose.runtime.Immutable


@Immutable
data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)
