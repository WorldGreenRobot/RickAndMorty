package com.green.robot.rickandmorty.domain.repository.character

import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getCharacters(
        options: Map<FilterType, String>
    ): Result<List<Character>>

    suspend fun getCharacterById(id: Int): Result<CharacterDetail>
}