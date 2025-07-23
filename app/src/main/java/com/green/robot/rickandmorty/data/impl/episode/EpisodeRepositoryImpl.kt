package com.green.robot.rickandmorty.data.impl.episode

import com.green.robot.rickandmorty.data.database.dao.EpisodeDao
import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapDbToDomain
import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapDomainToDb
import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapEpisodeNetworkToDomain
import com.green.robot.rickandmorty.data.mapper.episode.EpisodeMapper.mapToDomain
import com.green.robot.rickandmorty.data.network.service.episode.EpisodeService
import com.green.robot.rickandmorty.di.Dispatcher
import com.green.robot.rickandmorty.di.DispatchersType
import com.green.robot.rickandmorty.domain.entity.episode.Episode
import com.green.robot.rickandmorty.domain.repository.episode.EpisodesRepository
import com.green.robot.rickandmorty.utils.android.NetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val episodeService: EpisodeService,
    private val networkState: NetworkState,
    private val episodeDao: EpisodeDao,
    @Dispatcher(DispatchersType.IO) private val ioDispatcher: CoroutineDispatcher
) : EpisodesRepository {
    override suspend fun getEpisodesByIds(ids: List<String>): Result<List<Episode>> {
        return withContext(ioDispatcher) {
            try {
                val episodes = if (networkState.isAccessNetwork()) {
                    if (ids.size > 1) {
                        episodeService.getEpisodesByIds(
                            ids.joinToString(",")
                        ).mapToDomain()
                    } else {
                        listOf(
                            episodeService.getEpisodeById(ids.first()).mapEpisodeNetworkToDomain()
                        )
                    }.also {
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