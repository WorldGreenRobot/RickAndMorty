package com.green.robot.rickandmorty.presenter.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object Characters : NavKey

@Serializable
data class CharacterDetail(val id: Int, val characterName: String) : NavKey
