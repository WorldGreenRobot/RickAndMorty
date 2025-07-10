package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.presenter.ui.screen.characters.CharactersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::CharactersViewModel)
}