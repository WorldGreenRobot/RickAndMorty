package com.green.robot.rickandmorty.data.network.entity.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("air_date")
    val airDate: String?,
    @SerialName("episode")
    val episode: String?
)
