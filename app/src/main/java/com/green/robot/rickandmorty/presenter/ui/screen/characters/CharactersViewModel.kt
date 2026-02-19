package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.usecase.character.GetCharactersUseCase
import com.green.robot.rickandmorty.domain.usecase.filter.GetFilterUseCase
import com.green.robot.rickandmorty.domain.usecase.filter.SetFilterUseCase
import com.green.robot.rickandmorty.presenter.mapper.filter.FilterMapper.mapToUi
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getFilterUseCase: GetFilterUseCase,
    private val setFilterUseCase: SetFilterUseCase
) : ViewModel(), ContainerHost<CharactersState, Nothing> {
    override val container: Container<CharactersState, Nothing> = container(CharactersState())

    init {
        loadData()
        loadFilter()
    }

    fun loadData() = intent {
        reduce {
            state.copy(
                data = getCharactersUseCase(),
            )
        }
        val filter = getFilterUseCase()
        reduce {
            state.copy(
                filterData = filter.mapToUi(),
                search = filter[FilterType.NAME]
            )
        }
    }

    private fun loadFilter() = intent {
        val filter = getFilterUseCase()
        reduce {
            state.copy(
                filterData = filter.mapToUi(),
                search = filter[FilterType.NAME]
            )
        }
    }

    fun updateSearch(query: String) = intent {
        reduce {
            state.copy(
                search = query
            )
        }
    }

    fun requestSearch() = intent {
        setFilterUseCase(createCharactersQuery())
    }

    fun setFilter(status: String, gender: String, species: String) = intent {
        reduce {
            state.copy(
                filterData = CharactersState.FilterData(
                    status = status,
                    species = species,
                    gender = gender
                )
            )
        }
        setFilterUseCase(createCharactersQuery())
    }

    fun openFilterDialog() = intent {
        reduce {
            state.copy(
                dialogs = state.dialogs + CharactersDialog.FilterCharacters(
                    state.filterData ?: CharactersState.FilterData()
                )
            )
        }
    }

    fun closeFilterDialog(dialog: CharactersDialog) = intent {
        reduce {
            state.copy(
                dialogs = state.dialogs - dialog
            )
        }
    }

    private fun createCharactersQuery(): Map<FilterType, String> {
        val state = container.stateFlow.value
        val queries = mutableMapOf<FilterType, String>().apply {
            if (!state.search.isNullOrBlank()) put(FilterType.NAME, state.search)
            if (!state.filterData?.status.isNullOrBlank()) put(
                FilterType.STATUS,
                state.filterData.status
            )
            if (!state.filterData?.gender.isNullOrBlank()) put(
                FilterType.GENDER,
                state.filterData.gender
            )
            if (!state.filterData?.species.isNullOrBlank()) put(
                FilterType.SPECIES,
                state.filterData.species
            )
        }
        return queries
    }
}