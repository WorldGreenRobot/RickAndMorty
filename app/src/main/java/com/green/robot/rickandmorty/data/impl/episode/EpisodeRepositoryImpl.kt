package com.green.robot.rickandmorty.data.impl.episode

import com.green.robot.rickandmorty.data.database.dao.EpisodeDao
import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapDbToDomain
import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapDomainToDb
import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapToDomain
import com.green.robot.rickandmorty.data.network.service.episode.EpisodeService
import com.green.robot.rickandmorty.domain.entity.episode.Episode
import com.green.robot.rickandmorty.domain.repository.episode.EpisodesRepository
import com.green.robot.rickandmorty.utils.android.NetworkState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class EpisodeRepositoryImpl(
    private val episodeService: EpisodeService,
    private val networkState: NetworkState,
    private val episodeDao: EpisodeDao
) : EpisodesRepository {
    override suspend fun getEpisodesByIds(ids: List<String>): Result<List<Episode>> {
        return withContext(Dispatchers.IO) {
            try {
                val episodes = if (networkState.isAccessNetwork()) {
                    episodeService.getEpisodesByIds(
                        ids.joinToString(",")
                    ).mapToDomain()
                        .also {
                            episodeDao.insertAll(it.mapDomainToDb())
                        }

                } else {
                    episodeDao.getEpisodesById(ids.mapNotNull { it.toIntOrNull() }).mapDbToDomain()
                }

                Result.success(episodes)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}