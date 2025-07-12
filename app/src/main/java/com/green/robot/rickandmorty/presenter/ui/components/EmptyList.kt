package com.green.robot.rickandmorty.presenter.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.green.robot.rickandmorty.R
import com.green.robot.rickandmorty.presenter.ui.theme.Grey10

@Composable
fun EmptyList(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(86.dp),
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = Grey10
        )

        Text(
            modifier = modifier,
            text = stringResource(R.string.characters_not_found)
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun EmptyListPreview() {
    MaterialTheme {
        EmptyList()
    }
}