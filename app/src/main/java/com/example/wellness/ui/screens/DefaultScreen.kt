package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wellness.ui.components.LocalBoardPadding
import com.example.wellness.ui.components.LocalGridPadding

@Composable
fun DefaultScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val gridPadding: Dp = 10.dp
    val boardPadding: Dp = 20.dp

    CompositionLocalProvider(LocalGridPadding provides gridPadding, LocalBoardPadding provides boardPadding) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .padding(PaddingValues(LocalGridPadding.current * 2))
                .fillMaxSize()
        ) {
            content()
        }
    }
}