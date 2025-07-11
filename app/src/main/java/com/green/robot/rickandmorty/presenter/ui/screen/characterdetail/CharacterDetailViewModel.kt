package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.domain.entity.usecase.character.GetCharacterByIdUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharacterDetailViewModel(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel(), ContainerHost<CharacterDetailState, Nothing> {

    override val container: Container<CharacterDetailState, Nothing> =
        container(CharacterDetailState())

    fun loadData(id: Int) = intent {
        val characterDetail = getCharacterByIdUseCase(id)
        when {
            characterDetail.isSuccess -> reduce {
                state.copy(
                    showLoading = false,
                    data = characterDetail.getOrNull(),
                    error = null
                )
            }

            characterDetail.isFailure -> reduce {
                state.copy(
                    showLoading = false,
                    error = characterDetail.exceptionOrNull()?.message
                )
            }
        }
    }
}