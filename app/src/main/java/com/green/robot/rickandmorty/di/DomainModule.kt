package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.domain.entity.usecase.character.GetCharacterByIdUseCase
import com.green.robot.rickandmorty.domain.entity.usecase.character.GetCharactersUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterByIdUseCase(get(), get()) }
}