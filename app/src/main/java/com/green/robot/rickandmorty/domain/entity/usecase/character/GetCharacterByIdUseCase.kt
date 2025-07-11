package com.green.robot.rickandmorty.domain.entity.usecase.character

import androidx.core.net.toUri
import com.green.robot.rickandmorty.domain.entity.character.CharacterDetailData
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import com.green.robot.rickandmorty.domain.repository.episode.EpisodesRepository

class GetCharacterByIdUseCase(
    private val charactersRepository: CharactersRepository,
    private val episodesRepository: EpisodesRepository
) {
    suspend operator fun invoke(id: Int): Result<CharacterDetailData> {
        val characterResult = charactersRepository.getCharacterById(id)
        if(characterResult.isFailure) {
            return Result.failure(characterResult.exceptionOrNull()!!)
        }
        val character = characterResult.getOrNull()
        if(character == null) {
            return Result.failure(Exception("Character not found"))
        }
        val episodeIds = character.episodes.mapNotNull {
            it.toUri().lastPathSegment?.toIntOrNull()
        }
        val episodesResult = episodesRepository.getEpisodesByIds(episodeIds)
        if(episodesResult.isFailure) {
            return Result.failure(episodesResult.exceptionOrNull()!!)
        }
        val episodes = episodesResult.getOrNull().orEmpty()
        return Result.success(CharacterDetailData(character, episodes))
    }
}