package com.green.robot.rickandmorty.data.database.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.green.robot.rickandmorty.data.database.dao.CharactersDao
import com.green.robot.rickandmorty.data.database.entity.CharacterDb

@Database(
    version = 1,
    entities = [
        CharacterDb::class
    ]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun characterDao(): CharactersDao
}