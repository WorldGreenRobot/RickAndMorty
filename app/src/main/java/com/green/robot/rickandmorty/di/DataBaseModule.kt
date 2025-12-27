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
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "rick_and_morty"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @Provides
    @Singleton
    fun provideEpisodeDao(db: AppDatabase) = db.episodeDao()

    @Provides
    @Singleton
    fun provideLocationDao(db: AppDatabase) = db.locationDao()

    @Provides
    @Singleton
    fun provideCharacterDetailsDao(db: AppDatabase) = db.characterDetailsDao()

    @Provides
    @Singleton
    fun provideRemoteKeysDao(db: AppDatabase) = db.remoteKeysDao()

    @Provides
    @Singleton
    fun provideFilterDao(db: AppDatabase) = db.filterDao()
}