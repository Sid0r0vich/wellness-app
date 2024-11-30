package com.example.wellness.ui.screens.register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.ui.components.AuthEmailField
import com.example.wellness.ui.components.AuthPasswordField
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.screens.RegisterStepScreen
import com.example.wellness.ui.viewModels.RegisterViewModel

@Composable
fun EnterAdditionalScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onNextClick: () -> Unit
) {
    val uiState = viewModel.uiState

    RegisterStepScreen(
        buttonTextId = R.string.next,
        onNextClick = onNextClick
    ) {
        Header(R.string.additional)
        Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
        AuthEmailField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        AuthPasswordField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
    }
}