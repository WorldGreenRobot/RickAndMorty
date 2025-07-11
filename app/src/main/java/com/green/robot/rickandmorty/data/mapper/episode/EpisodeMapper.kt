package com.green.robot.rickandmorty.data.mapper.episode

import com.green.robot.rickandmorty.data.network.entity.episode.EpisodeResponse
import com.green.robot.rickandmorty.domain.entity.episode.Episode
import com.green.robot.rickandmorty.utils.parseServerDate

object EpisodeMapper {
    fun EpisodeResponse.mapToDomain(): Episode {
        return Episode(
            id = id?:0,
            name = name.orEmpty(),
            airDate = airDate.orEmpty(),
            episode = episode.orEmpty()
        )
    }
}