package com.example.wellness.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.ui.AppViewModelProvider
import com.example.wellness.ui.components.AuthenticationTrigger
import com.example.wellness.ui.components.EmailField
import com.example.wellness.ui.components.PasswordField
import com.example.wellness.ui.components.collectIsPressedAsStateValue

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onPerformRegister: () -> Unit,
    onLoginClick: () -> Unit
) {
    val uiState = viewModel.uiState
    if (uiState.emailSource.collectIsPressedAsStateValue())
        uiState.emailIsValidated = false
    if (uiState.passwordSource.collectIsPressedAsStateValue())
        uiState.passwordIsValidated = false

    var expanded by rememberSaveable { mutableStateOf(false) }

    AuthenticationTrigger(
        authState = viewModel.authState.observeAsState(),
        onPerformAuth = onPerformRegister
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.signup),
                style = MaterialTheme.typography.displayMedium,
            )
            Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
            EmailField(
                value = uiState.email,
                onValueChange = { uiState.email = it; uiState.emailIsValidated = false },
                interactionSource = uiState.emailSource,
                isError = uiState.emailIsValidated && !viewModel.validateEmailFormat(uiState.email)
            )
            Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
            PasswordField(
                value = uiState.password,
                onValueChange = { uiState.password = it; uiState.passwordIsValidated = false },
                interactionSource = uiState.passwordSource,
                isError = uiState.passwordIsValidated && !viewModel.validatePasswordFormat(uiState.password)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Man", "Woman").forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = { /*TODO*/ }
                    )
                }
            }
            Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
            Button(
                onClick = {
                    viewModel.signIn(uiState.email, uiState.password)
                    uiState.emailIsValidated = true
                    uiState.passwordIsValidated = true
                },
                enabled = uiState.email.isNotEmpty() && uiState.password.isNotEmpty(),
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = stringResource(R.string.perform_register)
                )
            }
            TextButton(
                onClick = onLoginClick,
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = stringResource(R.string.have_account)
                )
            }
        }
    }
}