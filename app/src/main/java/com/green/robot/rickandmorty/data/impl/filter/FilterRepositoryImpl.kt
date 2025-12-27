package com.green.robot.rickandmorty.data.impl.filter

import com.green.robot.rickandmorty.data.database.dao.FilterDao
import com.green.robot.rickandmorty.data.mapper.filter.FilterMapper.mapToDb
import com.green.robot.rickandmorty.data.mapper.filter.FilterMapper.mapToDomain
import com.green.robot.rickandmorty.di.Dispatcher
import com.green.robot.rickandmorty.di.DispatchersType
import com.green.robot.rickandmorty.domain.entity.character.FilterType
import com.green.robot.rickandmorty.domain.repository.filter.FilterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val filterDao: FilterDao,
    @Dispatcher(DispatchersType.IO) private val ioDispatcher: CoroutineDispatcher
) : FilterRepository {
    override suspend fun setFilter(filter: Map<FilterType, String>) {
        withContext(ioDispatcher) {
            filterDao.insert(filter.mapToDb())
        }
    }

    override suspend fun getFilter(): Map<FilterType, String> {
        return withContext(ioDispatcher) {
            filterDao.getAll().firstOrNull().mapToDomain()
        }
    }
}