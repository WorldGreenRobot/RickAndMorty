package com.green.robot.rickandmorty.data.network.service.episode

import com.green.robot.rickandmorty.data.network.entity.episode.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeService {

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(@Path("ids") ids: String): List<EpisodeResponse>

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: String): EpisodeResponse
}