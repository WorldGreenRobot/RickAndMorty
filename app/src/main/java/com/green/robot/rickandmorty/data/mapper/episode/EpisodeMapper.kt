package com.green.robot.rickandmorty.data.mapper.episode

import com.green.robot.rickandmorty.data.database.entity.EpisodeDb
import com.green.robot.rickandmorty.data.network.entity.episode.EpisodeResponse
import com.green.robot.rickandmorty.domain.entity.episode.Episode

object EpisodeMapper {

    fun List<EpisodeResponse>.mapToDomain(): List<Episode> {
        return this.map {
            Episode(
                id = it.id ?: 0,
                name = it.name.orEmpty(),
                airDate = it.airDate.orEmpty(),
                episode = it.episode.orEmpty()
            )
        }
    }

    fun List<Episode>.mapDomainToDb(): List<EpisodeDb> {
        return this.map {
            EpisodeDb(
                id = it.id,
                name = it.name,
                airDate = it.airDate,
                episode = it.episode
            )
        }
    }

    fun List<EpisodeDb>.mapDbToDomain(): List<Episode> {
        return this.map {
            Episode(
                id = it.id,
                name = it.name,
                airDate = it.airDate,
                episode = it.episode
            )
        }
    }
}