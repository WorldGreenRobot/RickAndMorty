package com.green.robot.rickandmorty.data.mapper.character

import androidx.paging.PagingData
import androidx.paging.map
import com.green.robot.rickandmorty.data.database.entity.CharacterDb
import com.green.robot.rickandmorty.data.network.entity.character.CharactersResponse
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status

object CharactersMapper {

    fun List<CharactersResponse.Result>.mapNetworkToDb(): List<CharacterDb> {
        return this.map {
            CharacterDb(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                status = it.status.orEmpty(),
                species = it.species.orEmpty(),
                gender = it.gender.orEmpty(),
                image = it.image.orEmpty()
            )
        }
    }

    fun PagingData<CharacterDb>.mapDbToDomain(): PagingData<Character> {
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