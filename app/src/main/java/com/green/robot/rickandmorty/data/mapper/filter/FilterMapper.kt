package com.green.robot.rickandmorty.data.mapper.filter

import com.green.robot.rickandmorty.data.database.entity.FilterDb
import com.green.robot.rickandmorty.domain.entity.character.FilterType

object FilterMapper {
    fun FilterDb?.mapToDomain(): Map<FilterType, String> {
        return this?.let {
            mapOf(
                FilterType.NAME to name.orEmpty(),
                FilterType.STATUS to status.orEmpty(),
                FilterType.GENDER to gender.orEmpty(),
                FilterType.SPECIES to species.orEmpty(),
            )
        } ?: mapOf(
            FilterType.NAME to "",
            FilterType.STATUS to "",
            FilterType.GENDER to "",
            FilterType.SPECIES to "",
        )
    }

    fun Map<FilterType, String>.mapToDb(): FilterDb {
        return if(isEmpty()) {
            FilterDb(
                name = null,
                status = null,
                gender = null,
                species = null,
            )
        } else {
            FilterDb(
                name = this[FilterType.NAME],
                status = this[FilterType.STATUS],
                gender = this[FilterType.GENDER],
                species = this[FilterType.SPECIES],
            )
        }
    }
}