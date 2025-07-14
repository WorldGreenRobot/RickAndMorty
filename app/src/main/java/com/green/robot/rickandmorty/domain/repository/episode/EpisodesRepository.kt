package com.green.robot.rickandmorty.domain.repository.episode

import com.green.robot.rickandmorty.domain.entity.episode.Episode

interface EpisodesRepository {
    suspend fun getEpisodesByIds(ids: List<String>): Result<List<Episode>>
}