package com.green.robot.rickandmorty.domain.repository.character

import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getAllCharacters(): Result<List<Character>>

    suspend fun getCharacterById(id: Int): Result<CharacterDetail>
}