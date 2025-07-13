package com.green.robot.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.green.robot.rickandmorty.data.database.core.AppDatabase
import org.koin.dsl.module

val databaseModule = module {

    factory {
        Room.databaseBuilder(get<Context>(), AppDatabase::class.java, "rick_and_morty").build()
    }

    factory {
        get<AppDatabase>().characterDao()
    }

    factory {
        get<AppDatabase>().episodeDao()
    }

    factory {
        get<AppDatabase>().locationDao()
    }

    factory {
        get<AppDatabase>().characterDetailsDao()
    }
}