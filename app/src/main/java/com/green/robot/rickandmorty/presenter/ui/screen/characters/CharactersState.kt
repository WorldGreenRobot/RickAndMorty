package com.green.robot.rickandmorty.presenter.ui.screen.characters

import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersState.FilterData

data class CharactersState(
    val showFirstLoading: Boolean = true,
    val showRefreshLoading: Boolean = false,
    val data: List<Character>? = null,
    val filterData: FilterData? = null,
    val error: String? = null,
    val dialogs: List<CharactersDialog> = emptyList()
) {
    data class FilterData(
        val name: String? = null,
        val status: String? = null,
        val species: String? = null,
        val type: String? = null,
        val gender: String? = null,
    )
}

sealed interface CharactersDialog{
    data class FilterCharacters(
        val filterData: FilterData? = null,
    ): CharactersDialog
}


