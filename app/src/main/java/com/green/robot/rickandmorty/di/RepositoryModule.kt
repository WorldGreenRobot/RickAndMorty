package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.data.impl.character.CharactersRepositoryImpl
import com.green.robot.rickandmorty.data.impl.episode.EpisodeRepositoryImpl
import com.green.robot.rickandmorty.data.impl.filter.FilterRepositoryImpl
import com.green.robot.rickandmorty.data.impl.location.LocationRepositoryImpl
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import com.green.robot.rickandmorty.domain.repository.episode.EpisodesRepository
import com.green.robot.rickandmorty.domain.repository.filter.FilterRepository
import com.green.robot.rickandmorty.domain.repository.locaion.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindCharactersRepository(impl: CharactersRepositoryImpl): CharactersRepository

    @Binds
    fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodesRepository

    @Binds
    fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    fun bindFilterRepository(impl: FilterRepositoryImpl): FilterRepository
}