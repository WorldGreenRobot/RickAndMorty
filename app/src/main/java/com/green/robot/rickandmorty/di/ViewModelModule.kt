package com.green.robot.rickandmorty.di

import androidx.lifecycle.ViewModel
import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.CharacterDetailViewModel
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    fun bindsCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    fun bindsCharacterDetailViewModel(viewModel: CharacterDetailViewModel): ViewModel
}