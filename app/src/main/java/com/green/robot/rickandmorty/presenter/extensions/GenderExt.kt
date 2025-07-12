package com.green.robot.rickandmorty.presenter.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import com.green.robot.rickandmorty.domain.entity.character.Gender

@Composable
@ReadOnlyComposable
fun Gender.getGenderString(): String {
    return this.name.lowercase().replaceFirstChar {
        it.uppercase()
    }
}

@Composable
@ReadOnlyComposable
fun List<Gender>.getGenderStrings(): List<String> {
    return this.map { it.getGenderString() }
}
