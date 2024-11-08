package com.example.wellness.ui.screens

import android.widget.Toast
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.ui.AppViewModelProvider

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onPerformLogin: () -> Unit,
) {
    val uiState = viewModel.uiState
    if (uiState.emailSource.collectIsPressedAsState().value)
        uiState.emailIsValidated = false
    if (uiState.passwordSource.collectIsPressedAsState().value)
        uiState.passwordIsValidated = false

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
    )

    val authState = viewModel.authState.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> onPerformLogin()
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.displayMedium,
            )
            Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { uiState.email = it; uiState.emailIsValidated = false },
                label = {
                    Text( text = stringResource(R.string.email_label) )
                },
                shape = RoundedCornerShape(20.dp),
                colors = textFieldColors,
                interactionSource = uiState.emailSource,
                isError = uiState.emailIsValidated && !viewModel.validateEmailFormat(uiState.email)
            )
            Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { uiState.password = it; uiState.passwordIsValidated = false },
                label = {
                    Text( text = stringResource(R.string.password_label) )
                },
                shape = RoundedCornerShape(20.dp),
                colors = textFieldColors,
                visualTransformation =
                    if (uiState.passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (uiState.passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description =
                        if (uiState.passwordVisible) stringResource(R.string.hide_password)
                        else stringResource(R.string.show_password)

                    IconButton(onClick = {uiState.passwordVisible = !uiState.passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                },
                interactionSource = uiState.passwordSource,
                isError = uiState.passwordIsValidated && !viewModel.validatePasswordFormat(uiState.password)
            )
            TextButton(
                onClick = {  },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text(
                    text = stringResource(R.string.forget_password)
                )
            }
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
                    text = stringResource(R.string.perform_login)
                )
            }
            TextButton(
                onClick = {  },
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = stringResource(R.string.have_not_account)
                )
            }
        }
    }
}