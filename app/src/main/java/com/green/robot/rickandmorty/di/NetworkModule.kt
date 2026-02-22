package com.green.robot.rickandmorty.di

import com.green.robot.rickandmorty.data.network.service.character.CharactersService
import com.green.robot.rickandmorty.data.network.service.episode.EpisodeService
import com.green.robot.rickandmorty.data.network.service.location.LocationService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun createOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().retryOnConnectionFailure(true).build()
    }

    @Provides
    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersService {
        return retrofit.create(CharactersService::class.java)
    }

    @Provides
    fun provideEpisodeService(retrofit: Retrofit): EpisodeService {
        return retrofit.create(EpisodeService::class.java)
    }

    @Provides
    fun provideLocationService(retrofit: Retrofit): LocationService {
        return retrofit.create(LocationService::class.java)
    }

}