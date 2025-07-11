package com.green.robot.rickandmorty.data.impl.character

import com.green.robot.rickandmorty.data.mapper.character.CharacterMapper.mapNetworkToDomain
import com.green.robot.rickandmorty.data.mapper.character.CharactersMapper.mapNetworkToDomain
import com.green.robot.rickandmorty.data.network.service.character.CharactersService
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharactersRepositoryImpl(
    private val charactersService: CharactersService
) : CharactersRepository {
    override suspend fun getAllCharacters(): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val characters = charactersService.getAllCharacter().results.mapNetworkToDomain()
                Result.success(characters)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getCharacterById(id: Int): Result<CharacterDetail> {
        return withContext(Dispatchers.IO) {
            try {
                val character = charactersService.getCharacterById(id).mapNetworkToDomain()
                Result.success(character)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
