package com.green.robot.rickandmorty.domain.entity.usecase.character

import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository

class GetCharactersUseCase(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(
        options: Map<String, String>
    ) = charactersRepository.getCharacters(options)
}