package com.green.robot.rickandmorty.presenter.mapper.filter

import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersState

object FilterMapper {
    fun Map<FilterType, String>.mapToUi(): CharactersState.FilterData {
        return CharactersState.FilterData(
            status = this[FilterType.STATUS] ?: "",
            species = this[FilterType.SPECIES] ?: "",
            gender = this[FilterType.GENDER] ?: ""
        )
    }
}