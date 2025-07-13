package com.green.robot.rickandmorty.domain.repository.locaion

import com.green.robot.rickandmorty.domain.entity.location.Location

interface LocationRepository {
    suspend fun getLocationsById(id: String): Result<Location>
}
