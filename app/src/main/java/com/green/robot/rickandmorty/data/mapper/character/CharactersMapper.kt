package com.green.robot.rickandmorty.data.mapper.character

import com.green.robot.rickandmorty.data.network.entity.character.CharactersResponse
import com.green.robot.rickandmorty.domain.entity.character.Character

object CharactersMapper {
    fun List<CharactersResponse.Result>.mapNetworkToDomain(): List<Character> {
        return this.map {
            Character(
                id = it.id ?: 0,
                name = it.name ?: "",
                status = it.status ?: "",
                species = it.species ?: "",
                gender = it.gender ?: "",
                image = it.image ?: ""
            )
        }
    }
}