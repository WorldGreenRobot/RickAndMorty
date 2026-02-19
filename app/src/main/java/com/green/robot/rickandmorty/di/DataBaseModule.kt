package com.green.robot.rickandmorty.di

import androidx.room.Room
import com.green.robot.rickandmorty.data.database.core.AppDatabase
import org.koin.dsl.module

val dataBaseModule = module {
    factory {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "rick_and_morty"
        ).build()
    }

    single { get<AppDatabase>().characterDao() }
    single { get<AppDatabase>().episodeDao() }
    single { get<AppDatabase>().locationDao() }
    single { get<AppDatabase>().characterDetailsDao() }
    single { get<AppDatabase>().remoteKeysDao() }
    single { get<AppDatabase>().filterDao() }
}