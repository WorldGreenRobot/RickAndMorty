package com.green.robot.rickandmorty.data.network.entity.location

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LocationResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("dimension")
    val dimension: String?
)
