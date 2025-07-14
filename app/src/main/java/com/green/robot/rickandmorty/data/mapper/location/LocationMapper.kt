package com.green.robot.rickandmorty.data.mapper.location

import com.green.robot.rickandmorty.data.database.entity.LocationDb
import com.green.robot.rickandmorty.data.network.entity.location.LocationResponse
import com.green.robot.rickandmorty.domain.entity.location.Location

object LocationMapper {
    fun LocationResponse.mapNetworkToDomain(): Location {
        return Location(
            id = id ?: 0,
            name = name.orEmpty(),
            type = type.orEmpty(),
            dimension = dimension.orEmpty(),
        )
    }

    fun Location.mapDomainToDb(): LocationDb {
        return LocationDb(
            id = id,
            name = name,
            type = type,
            dimension = dimension,
        )
    }

    fun LocationDb.mapDbToDomain(): Location {
        return Location(
            id = id,
            name = name,
            type = type,
            dimension = dimension,
        )
    }
}