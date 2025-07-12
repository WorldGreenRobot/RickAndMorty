package com.green.robot.rickandmorty.presenter.ui.screen.characters

import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersState.FilterData

data class CharactersState(
    val showFirstLoading: Boolean = true,
    val showRefreshLoading: Boolean = false,
    val data: List<Character>? = null,
    val search: String? = null,
    val filterData: FilterData? = null,
    val error: String? = null,
    val dialogs: List<CharactersDialog> = emptyList()
) {
    data class FilterData(
        val status: String? = null,
        val species: String? = null,
        val gender: String? = null,
    ) {
        val filters: List<FilterValue>
            get() {
                val tempFilter = mutableListOf<FilterValue>()
                if (!status.isNullOrBlank())
                    tempFilter.add(FilterValue(FilterType.STATUS, status))

                if (!species.isNullOrBlank())
                    tempFilter.add(FilterValue(FilterType.SPECIES, species))

                if (!gender.isNullOrBlank())
                    tempFilter.add(FilterValue(FilterType.GENDER, gender))

                return tempFilter
            }

        data class FilterValue(
            val type: FilterType,
            val value: String,
        )
    }
}


sealed interface CharactersDialog {
    data class FilterCharacters(
        val filterData: FilterData? = null,
    ) : CharactersDialog
}


