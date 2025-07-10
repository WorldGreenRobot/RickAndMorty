package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import com.green.robot.rickandmorty.data.entity.Character

data class CharacterDetailState(
    val showLoading: Boolean = true,
    val data: Character? = null,
    val error: String? = null
)
