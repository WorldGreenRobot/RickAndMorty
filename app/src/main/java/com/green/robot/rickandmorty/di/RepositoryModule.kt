package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.data.impl.character.CharactersRepositoryImpl
import com.green.robot.rickandmorty.data.impl.episode.EpisodeRepositoryImpl
import com.green.robot.rickandmorty.data.impl.filter.FilterRepositoryImpl
import com.green.robot.rickandmorty.data.impl.location.LocationRepositoryImpl
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import com.green.robot.rickandmorty.domain.repository.episode.EpisodesRepository
import com.green.robot.rickandmorty.domain.repository.filter.FilterRepository
import com.green.robot.rickandmorty.domain.repository.locaion.LocationRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single<CharactersRepository> { CharactersRepositoryImpl(get(), get(), get(), get(named("IO"))) }
    single<EpisodesRepository> { EpisodeRepositoryImpl(get(), get(), get(), get(named("IO"))) }
    single<LocationRepository> { LocationRepositoryImpl(get(), get(), get(), get(named("IO"))) }
    single<FilterRepository> { FilterRepositoryImpl(get(), get(named("IO"))) }
}