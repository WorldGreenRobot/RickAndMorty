package com.green.robot.rickandmorty.data.impl.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.green.robot.rickandmorty.data.database.core.AppDatabase
import com.green.robot.rickandmorty.data.mapper.character.CharacterMapper.mapDbToDomain
import com.green.robot.rickandmorty.data.mapper.character.CharacterMapper.mapDomainToDb
import com.green.robot.rickandmorty.data.mapper.character.CharacterMapper.mapNetworkToDomain
import com.green.robot.rickandmorty.data.mapper.character.CharactersMapper.mapDbToDomain
import com.green.robot.rickandmorty.data.network.mediator.CharactersMediator
import com.green.robot.rickandmorty.data.network.service.character.CharactersService
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import com.green.robot.rickandmorty.utils.android.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CharactersRepositoryImpl(
    private val charactersService: CharactersService,
    private val networkState: NetworkState,
    private val appDatabase: AppDatabase
) : CharactersRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getCharacters(
        options: Map<FilterType, String>
    ): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = CharactersMediator(
                appDatabase,
                charactersService
            ),
            pagingSourceFactory = {
                appDatabase.characterDao().searchCharacters(
                    options[FilterType.NAME],
                    options[FilterType.STATUS],
                    options[FilterType.SPECIES],
                    options[FilterType.GENDER]
                )
            }
        ).flow.map {
            it.mapDbToDomain()
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCharacterById(id: Int): Result<CharacterDetail?> {
        return withContext(Dispatchers.IO) {
            try {
                val character = if (networkState.isAccessNetwork()) {
                    charactersService.getCharacterById(id).mapNetworkToDomain()
                        .also {
                            appDatabase.characterDetailsDao().insert(it.mapDomainToDb())
                        }
                } else {
                    appDatabase.characterDetailsDao().getCharacterDetailById(id)?.mapDbToDomain()
                }
                Result.success(character)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
