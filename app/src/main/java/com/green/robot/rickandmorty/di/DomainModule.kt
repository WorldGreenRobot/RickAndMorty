package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.domain.entity.usecase.character.GetAllCharacterUseCase
import com.green.robot.rickandmorty.domain.entity.usecase.character.GetCharacterByIdUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetAllCharacterUseCase(get()) }
    factory { GetCharacterByIdUseCase(get(), get()) }
}