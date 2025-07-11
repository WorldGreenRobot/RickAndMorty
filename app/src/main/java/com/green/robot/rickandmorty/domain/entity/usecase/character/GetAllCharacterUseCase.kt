package com.green.robot.rickandmorty.domain.entity.usecase.character

import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository

class GetAllCharacterUseCase(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke() = charactersRepository.getAllCharacters()
}