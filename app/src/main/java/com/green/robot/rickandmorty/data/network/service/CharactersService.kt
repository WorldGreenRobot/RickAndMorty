package com.green.robot.rickandmorty.data.network.service

import com.green.robot.rickandmorty.data.network.entity.CharactersResponse
import retrofit2.http.GET

interface CharactersService {
    @GET("character")
    suspend fun getAllCharacter(): CharactersResponse
}