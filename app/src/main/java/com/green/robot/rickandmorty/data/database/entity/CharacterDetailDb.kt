package com.green.robot.rickandmorty.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status

@Entity(tableName = "character_detail")
data class CharacterDetailDb(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val originId: String? = null,
    val originName: String,
    val locationId: String? = null,
    val locationName: String,
    val image: String,
    val episodes: String
)
