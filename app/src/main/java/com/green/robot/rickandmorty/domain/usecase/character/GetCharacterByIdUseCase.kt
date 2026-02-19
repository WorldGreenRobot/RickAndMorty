package com.green.robot.rickandmorty.domain.usecase.character

import com.green.robot.rickandmorty.domain.entity.character.CharacterDetailData
import com.green.robot.rickandmorty.domain.entity.episode.Episode
import com.green.robot.rickandmorty.domain.entity.location.Location
import com.green.robot.rickandmorty.domain.repository.character.CharactersRepository
import com.green.robot.rickandmorty.domain.repository.episode.EpisodesRepository
import com.green.robot.rickandmorty.domain.repository.locaion.LocationRepository
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class GetCharacterByIdUseCase(
    private val charactersRepository: CharactersRepository,
    private val episodesRepository: EpisodesRepository,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(id: Int): Result<CharacterDetailData> {
        val characterResult = charactersRepository.getCharacterById(id)
        if (characterResult.isFailure) {
            return Result.failure(characterResult.exceptionOrNull()!!)
        }
        val character =
            characterResult.getOrNull() ?: return Result.failure(Exception("Character not found"))
        val episodeAsync: Deferred<Result<List<Episode>>>

        coroutineScope {
            episodeAsync = async {
                getEpisodes(character.episodes)
            }
        }

        val originResult = character.origin.id?.let {
            getLocation(it)
        }?.getOrNull()

        val locationResult = character.location.id?.let {
            getLocation(it)
        }?.getOrNull()

        val episodes = episodeAsync.await()

        if (episodes.isFailure) {
            return Result.failure(episodes.exceptionOrNull()!!)
        }

        val episodesResult = episodes.getOrNull()
        if (episodesResult == null) {
            return Result.failure(Exception("Episodes not found"))
        }

        return Result.success(
            CharacterDetailData(
                character,
                episodesResult,
                originResult,
                locationResult
            )
        )
    }

    private suspend fun getLocation(originId: String): Result<Location?> {
        val originAsync: Deferred<Result<Location?>>
        coroutineScope {
            originAsync = async {
                locationRepository.getLocationsById(originId)
            }
        }
        return originAsync.await()
    }

    private suspend fun getEpisodes(episodeIds: List<String>): Result<List<Episode>> {
        val episodesResult = episodesRepository.getEpisodesByIds(episodeIds)
        if (episodesResult.isFailure) {
            return Result.failure(episodesResult.exceptionOrNull()!!)
        }
        return Result.success(episodesResult.getOrNull().orEmpty())
    }
}