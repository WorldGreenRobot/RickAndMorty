package com.green.robot.rickandmorty.presenter.navigation

import kotlinx.serialization.Serializable

@Serializable
object Characters

@Serializable
data class CharacterDetail(val id: Int, val characterName: String)
