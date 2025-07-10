package com.green.robot.rickandmorty.presenter.ui.screen.characters

import com.green.robot.rickandmorty.data.entity.Character

data class CharactersState(
    val showLoading: Boolean = true,
    val data: List<Character>? = null,
    val error: String? = null
)
