package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.entity.usecase.character.GetCharactersUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel(), ContainerHost<CharactersState, Nothing> {
    override val container: Container<CharactersState, Nothing> = container(CharactersState())

    init {
        loadData()
    }

    fun loadData() = intent {
        val characters = getCharactersUseCase(createCharactersQuery())
        when {
            characters.isSuccess -> reduce {
                state.copy(
                    showFirstLoading = false,
                    showRefreshLoading = false,
                    data = characters.getOrNull(),
                    error = null
                )
            }

            characters.isFailure -> reduce {
                state.copy(
                    showFirstLoading = false,
                    showRefreshLoading = false,
                    error = characters.exceptionOrNull()?.message
                )
            }
        }
    }

    fun refresh() = intent {
        reduce {
            state.copy(
                showRefreshLoading = true
            )
        }
        loadData()
    }

    fun updateSearch(query: String) = intent {
        reduce {
            state.copy(
                showFirstLoading = true,
                search = query
            )
        }
        loadData()
    }

    fun searchCharacter() = intent {
        loadData()
    }

    fun setFilter(status: String, gender: String, race: String) = intent {
        reduce {
            state.copy(
                showFirstLoading = true,
                filterData = CharactersState.FilterData(
                    status = status,
                    species = race,
                    gender = gender
                )
            )
        }
        loadData()
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

    fun removeFilter(type: FilterType) = intent {
        when (type) {
            FilterType.GENDER -> reduce {
                state.copy(
                    showFirstLoading = true,
                    filterData = state.filterData?.copy(
                        gender = null
                    )
                )
            }

            FilterType.SPECIES -> reduce {
                state.copy(
                    showFirstLoading = true,
                    filterData = state.filterData?.copy(
                        species = null
                    )
                )
            }

            FilterType.STATUS -> reduce {
                state.copy(
                    showFirstLoading = true,
                    filterData = state.filterData?.copy(
                        status = null
                    )
                )
            }

            else -> {
                // no-op
            }
        }
        loadData()
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