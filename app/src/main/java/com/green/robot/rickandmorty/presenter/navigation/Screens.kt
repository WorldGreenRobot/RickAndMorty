package com.green.robot.rickandmorty.presenter.navigation

import kotlinx.serialization.Serializable


sealed interface AppRoute

@Serializable
object Characters: AppRoute

@Serializable
data class CharacterDetail(val id: Int, val characterName: String): AppRoute
