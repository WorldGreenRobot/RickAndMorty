package com.green.robot.rickandmorty.data.impl.filter

import com.green.robot.rickandmorty.data.database.dao.FilterDao
import com.green.robot.rickandmorty.data.mapper.filter.FilterMapper.mapToDb
import com.green.robot.rickandmorty.data.mapper.filter.FilterMapper.mapToDomain
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.repository.filter.FilterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FilterRepositoryImpl(
    private val filterDao: FilterDao
) : FilterRepository {
    override suspend fun setFilter(filter: Map<FilterType, String>) {
        withContext(Dispatchers.IO) {
            filterDao.insert(filter.mapToDb())
        }
    }

    override suspend fun getFilter(): Map<FilterType, String> {
        return withContext(Dispatchers.IO) {
            filterDao.getAll().firstOrNull().mapToDomain()
        }
    }
}