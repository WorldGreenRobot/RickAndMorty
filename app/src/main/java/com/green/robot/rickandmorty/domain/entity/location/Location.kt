package com.green.robot.rickandmorty.domain.entity.location

import androidx.compose.runtime.Immutable

@Immutable
data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)
