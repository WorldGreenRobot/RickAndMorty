package com.green.robot.rickandmorty.data.network.service.character

import com.green.robot.rickandmorty.data.network.entity.character.CharacterDetailsResponse
import com.green.robot.rickandmorty.data.network.entity.character.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {

    @GET("character")
    suspend fun getCharacters(@Query("page") id: Int): CharactersResponse

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterDetailsResponse

}