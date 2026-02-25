package com.green.robot.rickandmorty.presenter.ui.screen.characterdetail

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.domain.usecase.character.GetCharacterByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel(assistedFactory = CharacterDetailViewModel.Factory::class)
class CharacterDetailViewModel @AssistedInject constructor(
    @Assisted("id") private val id: Int,
    @Assisted("name") private val characterName: String,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel(), ContainerHost<CharacterDetailState, Nothing> {

    override val container: Container<CharacterDetailState, Nothing> =
        container(CharacterDetailState(name = characterName))

    init {
        loadData()
    }

    fun loadData() = intent {
        reduce {
            state.copy(
                showLoading = true,
                data = null,
                error = null
            )
        }
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

    fun refresh() = intent {
        reduce {
            state.copy(
                showLoading = true,
                error = null
            )
        }
        loadData()
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("id") id: Int,
            @Assisted("name") characterName: String
        ): CharacterDetailViewModel
    }
}