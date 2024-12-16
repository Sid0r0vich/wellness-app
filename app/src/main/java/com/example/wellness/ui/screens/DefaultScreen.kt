package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.wellness.ui.components.DefaultSpacer
import com.example.wellness.ui.components.HomeUserCard
import com.example.wellness.ui.components.LocalBoardPadding
import com.example.wellness.ui.components.LocalGridPadding
import com.example.wellness.ui.components.RightArrowButton
import com.example.wellness.ui.viewModels.UserViewModel

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

@Composable
fun AuthScreen(
    content: @Composable ColumnScope.() -> Unit
) {
    DefaultScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(LocalGridPadding.current)
        ) {
            content()
        }
    }
}

@Composable
fun RegisterStepScreen(
    buttonTextId: Int,
    onNextClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    DefaultScreen {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                content()
            }

            RightArrowButton(
                textId = buttonTextId,
                onClick = onNextClick,
                enabled = enabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
fun LazyScreen(
    content: LazyListScope.() -> Unit = {}
) {
    DefaultScreen {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxHeight()
        ) {
            content()
        }
    }
}

@Composable
fun UserScreen(
    uiState: UserViewModel.UiState,
    content: LazyListScope.() -> Unit = {}
) {
    LazyScreen {
        item {
            HomeUserCard(userName = uiState.userName)
        }
        content()
    }
}

@Composable
fun ExperimentalUserScreen(
    uiState: UserViewModel.UiState,
    content: LazyListScope.() -> Unit = {}
) {
    DefaultScreen {
        Column {
            HomeUserCard(userName = uiState.userName)
            DefaultSpacer()
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            ) {
                content()
            }
        }
    }
}