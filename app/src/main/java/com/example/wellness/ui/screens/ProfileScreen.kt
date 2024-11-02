package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val paddingColumn = PaddingValues(
        vertical = 10.dp,
        horizontal = 20.dp
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.align(alignment = Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UserCard(
                modifier = Modifier
                    .padding(PaddingValues(bottom = 40.dp))
                    .padding(paddingColumn)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun MockScreen(
    modifier: Modifier = Modifier
) { }