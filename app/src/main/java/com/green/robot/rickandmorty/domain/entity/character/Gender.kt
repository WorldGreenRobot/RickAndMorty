package com.green.robot.rickandmorty.domain.entity.character

enum class Gender {
    FEMALE, MALE, GENDERLESS, UNKNOWN;

    companion object {
        fun getGender(gender: String): Gender {
            return entries.find { it.name.equals(gender, ignoreCase = true) } ?: UNKNOWN
        }
    }
}


