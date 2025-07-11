package com.green.robot.rickandmorty.domain.repository.episode

import com.green.robot.rickandmorty.domain.entity.episode.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {
    suspend fun getEpisodesByIds(ids: List<Int>): Result<List<Episode>>
}