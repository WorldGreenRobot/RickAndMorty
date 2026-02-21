package com.green.robot.rickandmorty.domain.usecase.filter

import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.repository.filter.FilterRepository
import javax.inject.Inject

class SetFilterUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke(filter: Map<FilterType, String>) =
        filterRepository.setFilter(filter)
}