package com.green.robot.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.green.robot.rickandmorty.data.database.core.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty"
        ).build()
    }

    @Provides
    fun provideCharacterDao(appDatabase: AppDatabase) = appDatabase.characterDao()

    @Provides
    fun provideEpisodeDao(appDatabase: AppDatabase) = appDatabase.episodeDao()

    @Provides
    fun provideLocationDao(appDatabase: AppDatabase) = appDatabase.locationDao()

    @Provides
    fun provideCharacterDetailsDao(appDatabase: AppDatabase) = appDatabase.characterDetailsDao()

    @Provides
    fun provideRemoteKeysDao(appDatabase: AppDatabase) = appDatabase.remoteKeysDao()

    @Provides
    fun provideFilterDao(appDatabase: AppDatabase) = appDatabase.filterDao()
}