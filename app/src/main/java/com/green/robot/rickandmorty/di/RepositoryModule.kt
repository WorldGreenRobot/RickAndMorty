package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.data.impl.CharactersRepositoryImpl
import com.green.robot.rickandmorty.data.repository.CharactersRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<CharactersRepository> {
        CharactersRepositoryImpl(get())
    }
}