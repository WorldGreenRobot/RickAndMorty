package com.green.robot.rickandmorty.presenter.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = topBar
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content()
        }
    }
}