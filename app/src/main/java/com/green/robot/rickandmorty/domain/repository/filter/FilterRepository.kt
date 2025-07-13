package com.green.robot.rickandmorty.domain.repository.filter

import com.green.robot.rickandmorty.domain.entity.character.FilterType

interface FilterRepository {
    suspend fun setFilter(filter: Map<FilterType, String>)
    suspend fun getFilter(): Map<FilterType, String>
}