package com.green.robot.rickandmorty.data.impl

import com.green.robot.rickandmorty.data.entity.Character
import com.green.robot.rickandmorty.data.mapper.CharacterMapper.mapNetworkToDomain
import com.green.robot.rickandmorty.data.network.service.CharactersService
import com.green.robot.rickandmorty.data.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CharactersRepositoryImpl(
    private val charactersService: CharactersService
) : CharactersRepository {
    override fun getAllCharacters(): Flow<Result<List<Character>>> {
        return flow {
            try {
                val characters = charactersService.getAllCharacter().results.mapNetworkToDomain()
                emit(Result.success(characters))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }
}