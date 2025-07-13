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
        val character = characterResult.getOrNull()
        if (character == null) {
            return Result.failure(Exception("Character not found"))
        }
        val episodeAsync: Deferred<Result<List<Episode>>>

        val locationAsync: Deferred<Result<Location?>>
        coroutineScope {
            episodeAsync = async {
                getEpisodes(character.episodes)
            }
        }

        coroutineScope {
            locationAsync = async {
                getLocation(character.origin)
            }
        }

        val episodes = episodeAsync.await()
        val location = locationAsync.await()

        if (episodes.isFailure || location.isFailure) {
            return Result.failure(episodes.exceptionOrNull() ?: location.exceptionOrNull()!!)
        }

        val episodesResult = episodes.getOrNull()
        if (episodesResult == null) {
            return Result.failure(Exception("Episodes not found"))
        }
        val locationResult = location.getOrNull()
        if (locationResult == null) {
            return Result.failure(Exception("Location not found"))
        }

        return Result.success(CharacterDetailData(character, episodesResult, locationResult))
    }


    private suspend fun getLocation(locationId: String): Result<Location?> {
        val locationResult = locationRepository.getLocationsById(locationId)
        if (locationResult.isFailure) {
            return Result.failure(locationResult.exceptionOrNull()!!)
        }
        return Result.success(locationResult.getOrNull())
    }

    private suspend fun getEpisodes(episodeIds: List<String>): Result<List<Episode>> {
        val episodesResult = episodesRepository.getEpisodesByIds(episodeIds)
        if (episodesResult.isFailure) {
            return Result.failure(episodesResult.exceptionOrNull()!!)
        }
        return Result.success(episodesResult.getOrNull().orEmpty())
    }
}