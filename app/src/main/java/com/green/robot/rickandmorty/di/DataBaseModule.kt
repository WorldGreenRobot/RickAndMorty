package com.green.robot.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.green.robot.rickandmorty.data.database.core.AppDatabase
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    @Provides
    @AppScope
    fun provideDataBase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty"
        ).build()
    }

    @Provides
    @AppScope
    fun provideCharacterDao(appDatabase: AppDatabase) = appDatabase.characterDao()

    @Provides
    @AppScope
    fun provideEpisodeDao(appDatabase: AppDatabase) = appDatabase.episodeDao()

    @Provides
    @AppScope
    fun provideLocationDao(appDatabase: AppDatabase) = appDatabase.locationDao()

    @Provides
    @AppScope
    fun provideCharacterDetailsDao(appDatabase: AppDatabase) = appDatabase.characterDetailsDao()

    @Provides
    @AppScope
    fun provideRemoteKeysDao(appDatabase: AppDatabase) = appDatabase.remoteKeysDao()

    @Provides
    @AppScope
    fun provideFilterDao(appDatabase: AppDatabase) = appDatabase.filterDao()
}