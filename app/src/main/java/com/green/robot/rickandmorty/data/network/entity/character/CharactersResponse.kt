package com.green.robot.rickandmorty.data.network.entity.character

import com.green.robot.rickandmorty.data.network.entity.core.Info
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponse(
    @SerialName("info")
    val info: Info?,
    @SerialName("results")
    val results: List<Result>
) {
    @Serializable
    data class Result(
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
        @SerialName("image")
        val image: String?
    )
}
