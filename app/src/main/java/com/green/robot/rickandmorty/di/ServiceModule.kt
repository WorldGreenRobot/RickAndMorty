package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.data.network.service.character.CharactersService
import com.green.robot.rickandmorty.data.network.service.episode.EpisodeService
import com.green.robot.rickandmorty.data.network.service.location.LocationService
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    factory { get<Retrofit>().create(CharactersService::class.java) }
    factory { get<Retrofit>().create(EpisodeService::class.java) }
    factory { get<Retrofit>().create(LocationService::class.java) }
}