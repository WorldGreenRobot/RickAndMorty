package com.green.robot.rickandmorty.data.mapper

import com.green.robot.rickandmorty.data.entity.Character
import com.green.robot.rickandmorty.data.network.entity.CharacterResponse
import com.green.robot.rickandmorty.data.network.entity.CharactersResponse

object CharacterMapper {
    fun CharacterResponse.mapNetworkToDomain(): Character {
        return  Character(
            id = this.id ?: 0,
            name = this.name ?: "",
            status = this.status ?: "",
            species = this.species ?: "",
            gender = this.gender ?: "",
            image = this.image ?: ""
        )
    }
}