package com.green.robot.rickandmorty.presenter.ui.screen.characters

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.data.repository.CharactersRepository
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharactersViewModel (
    private val charactersRepository: CharactersRepository
): ViewModel(), ContainerHost<CharactersState, Nothing> {
    override val container: Container<CharactersState, Nothing> = container(CharactersState())

    init {
        loadData()
    }

    private fun loadData() = intent{
        charactersRepository.getAllCharacters()
            .collect {
                when{
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