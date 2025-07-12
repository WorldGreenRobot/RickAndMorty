package com.green.robot.rickandmorty.data.mapper.character

import com.green.robot.rickandmorty.data.database.entity.CharacterDb
import com.green.robot.rickandmorty.data.network.entity.character.CharactersResponse
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status

object CharactersMapper {
    fun List<CharactersResponse.Result>.mapNetworkToDomain(): List<Character> {
        return this.map {
            Character(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                status = Status.getStatus(it.status.orEmpty()),
                species = it.species.orEmpty(),
                gender = Gender.getGender(it.gender.orEmpty()),
                image = it.image.orEmpty()
            )
        }
    }

    fun List<Character>.mapDomainToDb(): List<CharacterDb> {
        return this.map {
            CharacterDb(
                id = it.id,
                name = it.name,
                status = it.status.name.lowercase().replaceFirstChar { it.uppercase() },
                species = it.species,
                gender = it.gender.name.lowercase().replaceFirstChar { it.uppercase() },
                image = it.image
            )
        }
    }

    fun List<CharacterDb>.mapDbToDomain(): List<Character> {
        return this.map {
            Character(
                id = it.id,
                name = it.name,
                status = Status.getStatus(it.status),
                species = it.species,
                gender = Gender.getGender(it.gender),
                image = it.image
            )
        }
    }
}