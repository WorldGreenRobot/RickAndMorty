package com.green.robot.rickandmorty.domain.usecase.filter

import com.green.robot.rickandmorty.domain.repository.filter.FilterRepository

class GetFilterUseCase(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke() = filterRepository.getFilter()
}