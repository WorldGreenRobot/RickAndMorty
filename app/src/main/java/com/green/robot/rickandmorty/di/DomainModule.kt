package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.domain.usecase.character.GetCharacterByIdUseCase
import com.green.robot.rickandmorty.domain.usecase.character.GetCharactersUseCase
import com.green.robot.rickandmorty.domain.usecase.filter.GetFilterUseCase
import com.green.robot.rickandmorty.domain.usecase.filter.SetFilterUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterByIdUseCase(get(), get(), get()) }
    factory { GetFilterUseCase(get()) }
    factory { SetFilterUseCase(get()) }

}