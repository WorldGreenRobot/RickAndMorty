package com.green.robot.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.green.robot.rickandmorty.data.database.entity.EpisodeDb

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episodes")
    fun getAll(): List<EpisodeDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(episodes: List<EpisodeDb>)

    @Query("DELETE FROM episodes")
    fun deleteAll()

    @Query("SELECT * FROM episodes WHERE id IN (:ids)")
    fun getEpisodesById(ids: List<Int>): List<EpisodeDb>

}