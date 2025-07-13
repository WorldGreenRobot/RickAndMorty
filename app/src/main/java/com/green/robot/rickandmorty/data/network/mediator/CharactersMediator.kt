package com.green.robot.rickandmorty.data.network.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.green.robot.rickandmorty.data.database.core.AppDatabase
import com.green.robot.rickandmorty.data.database.entity.CharacterDb
import com.green.robot.rickandmorty.data.database.entity.RemoteKeys
import com.green.robot.rickandmorty.data.mapper.character.CharactersMapper.mapNetworkToDb
import com.green.robot.rickandmorty.data.network.service.character.CharactersService
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class CharactersMediator(
    private val appDatabase: AppDatabase,
    private val character: CharactersService
) : RemoteMediator<Int, CharacterDb>() {
    private val characterDao = appDatabase.characterDao()
    private val remoteKeysDao = appDatabase.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (appDatabase.remoteKeysDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterDb>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevKey
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextKey
                }
            }

            val apiResult = character.getCharacters(page)

            val characters = apiResult.results
            val endOfPaginationReached = characters.isEmpty()

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    characterDao.deleteAllCharacters()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = characters.map {
                    RemoteKeys(
                        characterId = it.id ?: 0,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }
                remoteKeysDao.insertAll(remoteKeys)
                characterDao.insertCharacters(
                    characters.mapNetworkToDb()
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, CharacterDb>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeyByCharacterID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterDb>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                remoteKeysDao.getRemoteKeyByCharacterID(character.id)
            }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterDb>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                remoteKeysDao.getRemoteKeyByCharacterID(character.id)
            }
    }

}