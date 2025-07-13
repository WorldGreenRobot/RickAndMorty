package com.green.robot.rickandmorty.data.mapper.character

import androidx.core.net.toUri
import com.green.robot.rickandmorty.data.database.entity.CharacterDb
import com.green.robot.rickandmorty.data.network.entity.character.CharacterDetailsResponse
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import com.green.robot.rickandmorty.domain.entity.character.Gender
import com.green.robot.rickandmorty.domain.entity.character.Status

object CharacterMapper {
    fun CharacterDetailsResponse.mapNetworkToDomain(): CharacterDetail {
        return CharacterDetail(
            id = this.id ?: 0,
            name = this.name.orEmpty(),
            status = Status.getStatus(this.status.orEmpty()),
            species = this.species.orEmpty(),
            gender = Gender.getGender(this.gender.orEmpty()),
            originId = this.origin?.url?.toUri()?.lastPathSegment.orEmpty(),
            originName = this.origin?.name.orEmpty(),
            image = this.image.orEmpty(),
            episodes = this.episode?.map { it.toUri().lastPathSegment }
                .orEmpty()
                .filterNotNull()
        )
    }
}