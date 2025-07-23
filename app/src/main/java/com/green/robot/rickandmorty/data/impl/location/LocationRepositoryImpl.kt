package com.green.robot.rickandmorty.data.impl.location

import com.green.robot.rickandmorty.data.database.dao.LocationDao
import com.green.robot.rickandmorty.data.mapper.location.LocationMapper.mapDbToDomain
import com.green.robot.rickandmorty.data.mapper.location.LocationMapper.mapDomainToDb
import com.green.robot.rickandmorty.data.mapper.location.LocationMapper.mapNetworkToDomain
import com.green.robot.rickandmorty.data.network.service.location.LocationService
import com.green.robot.rickandmorty.di.Dispatcher
import com.green.robot.rickandmorty.di.DispatchersType
import com.green.robot.rickandmorty.domain.entity.location.Location
import com.green.robot.rickandmorty.domain.repository.locaion.LocationRepository
import com.green.robot.rickandmorty.utils.android.NetworkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService,
    private val networkState: NetworkState,
    private val locationDao: LocationDao,
    @Dispatcher(DispatchersType.IO) private val ioDispatcher: CoroutineDispatcher
) : LocationRepository {
    override suspend fun getLocationsById(id: String): Result<Location> {
        return withContext(ioDispatcher) {
            try {
                val location = if (networkState.isAccessNetwork()) {
                    locationService.getLocationsById(id).mapNetworkToDomain()
                        .also {
                            locationDao.insert(it.mapDomainToDb())
                        }
                } else {
                    locationDao.getLocationById(id.toInt()).mapDbToDomain()
                }
                Result.success(location)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    }
}