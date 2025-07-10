package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.data.repository.CharactersRepository
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharacterDetailViewModel(
    private val charactersRepository: CharactersRepository
) : ViewModel(), ContainerHost<CharacterDetailState, Nothing> {

    override val container: Container<CharacterDetailState, Nothing> = container(CharacterDetailState())

    fun loadData(id: Int) = intent {
        charactersRepository.getCharacterById(id)
            .collect {
                when {
                    it.isSuccess -> reduce {
                        state.copy(
                            showLoading = false,
                            data = it.getOrNull(),
                            error = null
                        )
                    }

                    it.isFailure -> reduce {
                        state.copy(
                            showLoading = false,
                            error = it.exceptionOrNull()?.message
                        )
                    }
                }
            }

    }

}