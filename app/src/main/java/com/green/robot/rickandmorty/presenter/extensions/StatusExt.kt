package com.green.robot.rickandmorty.presenter.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.green.robot.rickandmorty.domain.entity.character.Status

@Composable
@ReadOnlyComposable
fun Status.getStatusString(): String {
    return this.name.lowercase().replaceFirstChar {
        it.uppercase()
    }
}

@Composable
fun Status.getStatusColor(): Color {
    return when (this) {
        Status.ALIVE -> Color.Green
        Status.DEAD -> Color.Red
        Status.UNKNOWN -> Color.Gray
        else -> Color.Gray
    }
}

@Composable
@ReadOnlyComposable
fun List<Status>.getStatusStrings(): List<String> {
    return this.map { it.getStatusString() }
}