package com.green.robot.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "episodes")
data class EpisodeDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("air_date")
    val airDate: String,
    @SerialName("episode")
    val episode: String
)
