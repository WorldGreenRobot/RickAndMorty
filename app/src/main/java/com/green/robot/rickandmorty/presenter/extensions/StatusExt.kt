package com.green.robot.rickandmorty.presenter.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.green.robot.rickandmorty.domain.entity.character.Status

@Composable
@ReadOnlyComposable
fun Status.getStatusString(): String {
    return this.name.lowercase().replaceFirstChar {
        it.uppercase()
    }
}

@Composable
fun Status?.getStatusColor(): Color {
    return this?.let {
        when (it) {
            Status.ALIVE -> Color.Green
            Status.DEAD -> Color.Red
            Status.UNKNOWN -> Color.Gray
            else -> Color.Gray
        }
    } ?: Color.Gray
}

@Composable
@ReadOnlyComposable
fun List<Status>.getStatusStrings(): List<String> {
    return this.map { it.getStatusString() }
}