package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import com.green.robot.rickandmorty.domain.entity.character.CharacterDetailData

data class CharacterDetailState(
    val showLoading: Boolean = true,
    val data: CharacterDetailData? = null,
    val error: String? = null
)
