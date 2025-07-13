package com.green.robot.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_filter")
data class FilterDb(
    @PrimaryKey
    val id: Int = 1,
    @ColumnInfo(name="name")
    val name: String? = null,
    @ColumnInfo(name="status")
    val status: String? = null,
    @ColumnInfo(name="species")
    val species: String? = null,
    @ColumnInfo(name="gender")
    val gender: String? = null,
)