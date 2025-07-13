package com.green.robot.rickandmorty.domain.usecase.character

import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository

class GetCharactersUseCase(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(
    ) = charactersRepository.getCharacters()
}