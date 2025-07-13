package com.green.robot.rickandmorty.data.mapper.character

import androidx.core.net.toUri
import com.green.robot.rickandmorty.data.database.entity.CharacterDetailDb
import com.green.robot.rickandmorty.data.network.entity.character.CharacterDetailsResponse
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
            origin = CharacterDetail.Location(
                id = this.origin?.url?.toUri()?.lastPathSegment,
                name = this.origin?.name.orEmpty()
            ),
            location = CharacterDetail.Location(
                id = this.location?.url?.toUri()?.lastPathSegment,
                name = this.location?.name.orEmpty()
            ),
            image = this.image.orEmpty(),
            episodes = this.episode?.map { it.toUri().lastPathSegment }
                .orEmpty()
                .filterNotNull()
        )
    }

    fun CharacterDetail.mapDomainToDb(): CharacterDetailDb {
        return CharacterDetailDb(
            id = this.id,
            name = this.name,
            status = this.status.name,
            species = this.species,
            gender = this.gender.name,
            originId = this.origin.id,
            originName = this.origin.name,
            locationId = this.location.id,
            locationName = this.location.name,
            image = this.image,
            episodes = this.episodes.joinToString(",")
        )
    }

    fun CharacterDetailDb.mapDbToDomain(): CharacterDetail {
        return CharacterDetail(
            id = this.id,
            name = this.name,
            status = Status.valueOf(this.status),
            species = this.species,
            gender = Gender.valueOf(this.gender),
            origin = CharacterDetail.Location(
                id = this.originId,
                name = this.originName
            ),
            location = CharacterDetail.Location(
                id = this.locationId,
                name = this.locationName
            ),
            image = this.image,
            episodes = this.episodes.split(",")
        )
    }
}
