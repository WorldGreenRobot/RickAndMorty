package com.green.robot.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.green.robot.rickandmorty.data.database.core.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(appDatabase: AppDatabase) = appDatabase.characterDao()

    @Provides
    @Singleton
    fun provideEpisodeDao(appDatabase: AppDatabase) = appDatabase.episodeDao()

    @Provides
    @Singleton
    fun provideLocationDao(appDatabase: AppDatabase) = appDatabase.locationDao()

    @Provides
    @Singleton
    fun provideCharacterDetailsDao(appDatabase: AppDatabase) = appDatabase.characterDetailsDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(appDatabase: AppDatabase) = appDatabase.remoteKeysDao()

    @Provides
    @Singleton
    fun provideFilterDao(appDatabase: AppDatabase) = appDatabase.filterDao()
}