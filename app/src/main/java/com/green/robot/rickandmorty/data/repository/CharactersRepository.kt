package com.green.robot.rickandmorty.data.repository

import com.green.robot.rickandmorty.data.entity.Character
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getAllCharacters(): Flow<Result<List<Character>>>
}