package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.lifecycle.ViewModel
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
                filterData = state.filterData?.copy(
                    name = query
                )
            )
        }
    }

    fun searchCharacter(query: String) = intent {
        reduce {
            state.copy(
                showRefreshLoading = true
            )
        }
        val characters = getCharactersUseCase(mapOf("name" to query))
        reduce {
            state.copy(
                showRefreshLoading = false,
                data = characters.getOrNull(),
                error = null
            )
        }
    }

    fun openFilterDialog() = intent {
        reduce {
            state.copy(
                dialogs = state.dialogs + CharactersDialog.FilterCharacters(

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

    private fun createCharactersQuery(): Map<String, String> {
        return hashMapOf()
    }
}