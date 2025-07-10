package com.green.robot.rickandmorty.data.network.service

import com.green.robot.rickandmorty.data.network.entity.CharacterResponse
import com.green.robot.rickandmorty.data.network.entity.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CharactersService {
    @GET("character")
    suspend fun getAllCharacter(): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterResponse

}