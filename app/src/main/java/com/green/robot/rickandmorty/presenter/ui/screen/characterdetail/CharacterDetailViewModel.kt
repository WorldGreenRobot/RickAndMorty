package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.domain.usecase.character.GetCharacterByIdUseCase
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class CharacterDetailViewModel(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel(), ContainerHost<CharacterDetailState, Nothing> {

    override val container: Container<CharacterDetailState, Nothing> =
        container(CharacterDetailState())

    private var id: Int = 0

    fun loadData(id: Int) = intent {
        this@CharacterDetailViewModel.id = id
        val characterDetail = getCharacterByIdUseCase(id)
        when {
            characterDetail.isSuccess -> reduce {
                state.copy(
                    showLoading = false,
                    showRefresh = false,
                    data = characterDetail.getOrNull(),
                    error = null
                )
            }

            characterDetail.isFailure -> reduce {
                state.copy(
                    showLoading = false,
                    showRefresh = false,
                    error = characterDetail.exceptionOrNull()?.message
                )
            }
        }
    }

    fun refresh() = intent {
        reduce {
            state.copy(
                showRefresh = state.data == null,
                showLoading = state.data != null,
                error = null
            )
        }
        loadData(id)
    }
}