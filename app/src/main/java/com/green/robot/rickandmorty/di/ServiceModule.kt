package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.data.network.service.CharactersService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    factory<CharactersService> {
        get<Retrofit>().create(CharactersService::class.java)
    }
}