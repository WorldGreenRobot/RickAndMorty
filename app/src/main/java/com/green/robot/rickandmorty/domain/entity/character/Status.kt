package com.green.robot.rickandmorty.domain.entity.character

enum class Status {
    ALIVE, DEAD, UNKNOWN;

    companion object {
        fun getStatus(status: String): Status {
            return entries.firstOrNull { it.name.equals(status, ignoreCase = true) } ?: UNKNOWN
        }
    }
}