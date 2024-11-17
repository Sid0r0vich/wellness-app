package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.ui.components.UnauthenticatedTrigger
import com.example.wellness.ui.components.UserCard
import com.example.wellness.ui.viewModels.AppViewModelProvider
import com.example.wellness.ui.viewModels.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onUnauthenticated: () -> Unit
) {
    UnauthenticatedTrigger(
        authState = viewModel.authStateFlow.collectAsState(),
        onUnauthenticated = onUnauthenticated
    )

    DefaultScreen {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxHeight()
        ) {
            ProfileUserCard(viewModel.uiState.collectAsState().value.userName) {
                viewModel.signOut()
            }
        }
    }
}

@Composable
fun ProfileUserCard(
    userName: String,
    buttonOnClick: () -> Unit,
) {
    UserCard(
        modifier = Modifier
            .padding(
                PaddingValues(
                    vertical = 10.dp,
                    horizontal = 20.dp
                )
            )
            .fillMaxWidth(),
        userName = userName,
        addButton = true,
        buttonOnClick = buttonOnClick,
        avatarModifier = Modifier.size(150.dp)
    )
}

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.empty_screen_title),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(id = R.string.empty_screen_subtitle),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.outline
        )
    }
}