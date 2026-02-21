package com.green.robot.rickandmorty.domain.usecase.character

import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(
    ) = charactersRepository.getCharacters()
}