package com.green.robot.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.green.robot.rickandmorty.data.database.entity.LocationDb

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    fun getAll(): List<LocationDb>

    @Query("SELECT * FROM locations WHERE id = :id")
    fun getById(id: Int): LocationDb

    @Insert
    fun insert(vararg locations: LocationDb)

    @Query("DELETE FROM locations")
    fun deleteAll()

    @Query("SELECT * FROM locations WHERE id = :id")
    fun getLocationById(id: Int): LocationDb

}