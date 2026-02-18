package com.green.robot.rickandmorty.domain.repository.character

import androidx.paging.PagingData
import com.green.robot.rickandmorty.domain.entity.character.Character
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetail
import kotlinx.coroutines.flow.Flow


interface CharactersRepository {
    fun getCharacters(): Flow<PagingData<Character>>

    suspend fun getCharacterById(id: Int): Result<CharacterDetail?>
}