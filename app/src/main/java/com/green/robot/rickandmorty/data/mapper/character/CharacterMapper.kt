package com.green.robot.rickandmorty.data.mapper.character

import com.green.robot.rickandmorty.data.network.entity.character.CharacterDetailsResponse
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail

object CharacterMapper {
    fun CharacterDetailsResponse.mapNetworkToDomain(): CharacterDetail {
        return  CharacterDetail(
            id = this.id ?: 0,
            name = this.name ?: "",
            status = this.status ?: "",
            species = this.species ?: "",
            gender = this.gender ?: "",
            origin = this.origin?.name ?: "",
            image = this.image ?: "",
            episodes = this.episode.orEmpty()
        )
    }
}