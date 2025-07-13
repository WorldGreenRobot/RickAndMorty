package com.green.robot.rickandmorty.data.impl.character

import com.green.robot.rickandmorty.data.database.dao.CharacterDao
import com.green.robot.rickandmorty.data.database.dao.CharacterDetailDao
import com.green.robot.rickandmorty.data.mapper.character.CharacterMapper.mapDbToDomain
import com.green.robot.rickandmorty.data.mapper.character.CharacterMapper.mapDomainToDb
import com.green.robot.rickandmorty.data.mapper.character.CharacterMapper.mapNetworkToDomain
import com.green.robot.rickandmorty.data.mapper.character.CharactersMapper.mapDbToDomain
import com.green.robot.rickandmorty.data.mapper.character.CharactersMapper.mapDomainToDb
import com.green.robot.rickandmorty.data.mapper.character.CharactersMapper.mapNetworkToDomain
import com.green.robot.rickandmorty.data.network.service.character.CharactersService
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import com.green.robot.rickandmorty.utils.android.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersRepositoryImpl(
    private val charactersService: CharactersService,
    private val networkState: NetworkState,
    private val charactersDao: CharacterDao,
    private val characterDetailDao: CharacterDetailDao
) : CharactersRepository {
    override suspend fun getCharacters(
        options: Map<FilterType, String>
    ): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val characters = if (networkState.isAccessNetwork()) {
                    val customOptions = options.mapKeys {
                        it.key.name
                    }
                    val newCharacters =
                        charactersService.getCharacters(customOptions).results.mapNetworkToDomain()
                    charactersDao.insertCharacters(newCharacters.mapDomainToDb())
                    newCharacters
                } else {
                    charactersDao.searchCharacters(
                        options[FilterType.NAME],
                        options[FilterType.STATUS],
                        options[FilterType.SPECIES],
                        options[FilterType.GENDER]
                    ).mapDbToDomain()
                }
                Result.success(characters)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getCharacterById(id: Int): Result<CharacterDetail?> {
        return withContext(Dispatchers.IO) {
            try {
                val character = if (networkState.isAccessNetwork()) {
                    charactersService.getCharacterById(id).mapNetworkToDomain()
                        .also {
                            characterDetailDao.insert(it.mapDomainToDb())
                        }
                } else {
                    characterDetailDao.getCharacterDetailById(id)?.mapDbToDomain()
                }
                Result.success(character)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
