package com.example.wellness.ui.screens.register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.wellness.R
import com.example.wellness.auth.AuthState
import com.example.wellness.ui.components.AuthEmailInputField
import com.example.wellness.ui.components.AuthNameInputField
import com.example.wellness.ui.components.AuthPasswordInputField
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.components.RegisterFieldsInvalidation
import com.example.wellness.ui.screens.RegisterStepScreen
import com.example.wellness.ui.viewModels.EnterStepsViewModel

@Composable
fun EnterCredentialsScreen(
    modifier: Modifier = Modifier,
    viewModel: EnterStepsViewModel,
    onLoginClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val uiState = viewModel.uiState
    RegisterFieldsInvalidation(uiState)

    RegisterStepScreen(
        buttonTextId = R.string.next,
        onNextClick = onNextClick,
        enabled =
            uiState.email.isNotEmpty() &&
            uiState.password.isNotEmpty() &&
            uiState.name.isNotEmpty() &&
            viewModel.authState != AuthState.Loading,
    ) {
        Header(R.string.credentials)
        Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
        AuthNameInputField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        AuthEmailInputField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        AuthPasswordInputField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        TextButton(
            onClick = onLoginClick,
        ) {
            Text(text = stringResource(R.string.have_account))
        }
    }
}