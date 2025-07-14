package com.green.robot.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_detail")
data class CharacterDetailDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "status")
    val status: String,
    @ColumnInfo(name = "species")
    val species: String,
    @ColumnInfo(name = "gender")
    val gender: String,
    @ColumnInfo(name = "origin_id")
    val originId: String? = null,
    @ColumnInfo(name = "origin_name")
    val originName: String,
    @ColumnInfo(name = "location_id")
    val locationId: String? = null,
    @ColumnInfo(name = "location_name")
    val locationName: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "episode")
    val episodes: String
)
