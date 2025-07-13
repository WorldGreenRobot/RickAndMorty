package com.green.robot.rickandmorty.data.network.service.location

import com.green.robot.rickandmorty.data.network.entity.location.LocationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationService {

    @GET("location/{id}")
    suspend fun getLocationsById(@Path("id") id: String): LocationResponse
}