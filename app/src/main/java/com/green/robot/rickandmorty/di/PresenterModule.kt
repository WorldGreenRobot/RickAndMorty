package com.green.robot.rickandmorty.di


import com.green.robot.rickandmorty.presenter.ui.screen.characterdetail.CharacterDetailViewModel
import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presenterModule = module {
    viewModel { CharactersViewModel(get(), get(), get()) }
    viewModel { CharacterDetailViewModel(get()) }
}