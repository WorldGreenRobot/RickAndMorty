package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.domain.entity.usecase.character.GetAllCharacterUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharactersViewModel(
    private val getAllCharacterUseCase: GetAllCharacterUseCase
) : ViewModel(), ContainerHost<CharactersState, Nothing> {
    override val container: Container<CharactersState, Nothing> = container(CharactersState())

    init {
        loadData()
    }

    private fun loadData() = intent {
        val characters = getAllCharacterUseCase()
        when {
            characters.isSuccess -> reduce {
                state.copy(
                    showLoading = false,
                    data = characters.getOrNull(),
                    error = null
                )
            }

            characters.isFailure -> reduce {
                state.copy(
                    showLoading = false,
                    error = characters.exceptionOrNull()?.message
                )
            }
        }
    }
}