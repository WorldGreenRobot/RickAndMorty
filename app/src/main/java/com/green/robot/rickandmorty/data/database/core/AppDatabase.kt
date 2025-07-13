package com.green.robot.rickandmorty.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.green.robot.rickandmorty.data.database.dao.CharacterDao
import com.green.robot.rickandmorty.data.database.dao.CharacterDetailDao
import com.green.robot.rickandmorty.data.database.dao.EpisodeDao
import com.green.robot.rickandmorty.data.database.dao.LocationDao
import com.green.robot.rickandmorty.data.database.dao.RemoteKeysDao
import com.green.robot.rickandmorty.data.database.entity.CharacterDb
import com.green.robot.rickandmorty.data.database.entity.CharacterDetailDb
import com.green.robot.rickandmorty.data.database.entity.EpisodeDb
import com.green.robot.rickandmorty.data.database.entity.LocationDb
import com.green.robot.rickandmorty.data.database.entity.RemoteKeys

@Database(
    version = 1,
    entities = [
        CharacterDb::class,
        EpisodeDb::class,
        LocationDb::class,
        CharacterDetailDb::class,
        RemoteKeys::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun locationDao(): LocationDao
    abstract fun characterDetailsDao(): CharacterDetailDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}