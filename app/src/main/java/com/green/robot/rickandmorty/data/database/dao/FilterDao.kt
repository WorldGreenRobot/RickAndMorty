package com.green.robot.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.green.robot.rickandmorty.data.database.entity.FilterDb

@Dao
interface FilterDao {
    @Query("SELECT * FROM character_filter")
    fun getAll(): List<FilterDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(filter: FilterDb)
}