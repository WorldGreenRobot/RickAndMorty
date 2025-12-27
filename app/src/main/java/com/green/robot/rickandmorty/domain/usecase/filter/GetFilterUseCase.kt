package com.green.robot.rickandmorty.domain.usecase.filter

import com.green.robot.rickandmorty.domain.repository.filter.FilterRepository
import javax.inject.Inject

class GetFilterUseCase @Inject constructor(
    private val filterRepository: FilterRepository
) {
    suspend operator fun invoke() = filterRepository.getFilter()
}