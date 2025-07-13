package com.green.robot.rickandmorty.data.network.entity.character

import com.green.robot.rickandmorty.data.network.entity.location.LocationResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailsResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("status")
    val status: String?,
    @SerialName("species")
    val species: String?,
    @SerialName("gender")
    val gender: String?,
    @SerialName("origin")
    val origin: LocationResponse?,
    @SerialName("location")
    val location: LocationResponse?,
    @SerialName("image")
    val image: String?,
    @SerialName("episode")
    val episode: List<String>?
) {
    @Serializable
    data class LocationResponse(
        @SerialName("name")
        val name: String?,
        @SerialName("url")
        val url: String?
    )
}