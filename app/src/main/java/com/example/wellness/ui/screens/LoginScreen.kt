package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wellness.R
import com.example.wellness.auth.AuthData
import com.example.wellness.auth.AuthMessageNotifier
import com.example.wellness.auth.AuthStatus
import com.example.wellness.ui.components.AuthButton
import com.example.wellness.ui.components.AuthEmailInputField
import com.example.wellness.ui.components.AuthFieldsInvalidation
import com.example.wellness.ui.components.AuthPasswordInputField
import com.example.wellness.ui.components.Header
import com.example.wellness.ui.viewModels.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onPerformLogin: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    if (viewModel.isAuthenticated()) onPerformLogin()

    val messageNotifier = AuthMessageNotifier(LocalContext.current)
    val uiState = viewModel.uiState
    AuthFieldsInvalidation(uiState)

    AuthScreen {
        Header(R.string.login)
        Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
        AuthEmailInputField(uiState = uiState)
        Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
        AuthPasswordInputField(uiState = uiState)
        TextButton(
            onClick = {  },
        ) {
            Text(
                text = stringResource(R.string.forget_password)
            )
        }
        AuthButton(
            authState = viewModel.authState,
            uiState = uiState,
            textId = R.string.perform_login
        ) {
            viewModel.signIn(
                AuthData(
                    uiState.email,
                    uiState.password
                )
            ) { status ->
                messageNotifier.notifyUser(status)
                if (status == AuthStatus.SUCCESS) onPerformLogin()
            }
        }
        TextButton(
            onClick = onRegisterClick,
        ) {
            Text(
                text = stringResource(R.string.have_not_account)
            )
        }
    }
}