package com.green.robot.rickandmorty.presenter.ui.components.chip

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FilterInputChip(
    text: String,
    onDismiss: () -> Unit,
) {

    FilterChip(
        onClick = {},
        label = { Text(text) },
        selected = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Localized description",
                Modifier
                    .size(InputChipDefaults.AvatarSize)
                    .clickable {
                        onDismiss()
                    },
            )
        }
    )
}