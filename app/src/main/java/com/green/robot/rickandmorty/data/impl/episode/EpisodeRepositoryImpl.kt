package com.green.robot.rickandmorty.data.impl.episode

import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapToDomain
import com.green.robot.rickandmorty.data.network.service.episode.EpisodeService
import com.green.robot.rickandmorty.domain.entity.episode.Episode
import com.green.robot.rickandmorty.domain.repository.episode.EpisodesRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class EpisodeRepositoryImpl(
    private val episodeService: EpisodeService
) : EpisodesRepository {
    override suspend fun getEpisodesByIds(ids: List<Int>): Result<List<Episode>> {
        return try {
            val episodes = coroutineScope {
                val asyncs = mutableListOf<Deferred<Episode>>()
                ids.forEach {
                    asyncs.add(async(Dispatchers.IO) {
                        episodeService.getEpisodeById(it).mapToDomain()
                    })
                }
                asyncs.awaitAll()
            }
            Result.success(episodes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}