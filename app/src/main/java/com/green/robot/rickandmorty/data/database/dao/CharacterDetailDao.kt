package com.green.robot.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.green.robot.rickandmorty.data.database.entity.CharacterDetailDb

@Dao
interface CharacterDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detail: CharacterDetailDb)

    @Query("SELECT * FROM character_detail WHERE id = :id")
    suspend fun getCharacterDetailById(id: Int): CharacterDetailDb?

    @Delete
    suspend fun delete(detail: CharacterDetailDb)

}