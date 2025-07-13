package com.green.robot.rickandmorty.domain.usecase.character

import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository

class GetCharactersUseCase(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(
        options: Map<FilterType, String>
    ) = charactersRepository.getCharacters(options)
}