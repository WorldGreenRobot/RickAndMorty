package com.green.robot.rickandmorty.data.mapper

import com.green.robot.rickandmorty.data.network.entity.CharactersResponse
import com.green.robot.rickandmorty.data.entity.Character

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