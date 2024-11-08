package com.example.wellness.ui.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wellness.R
import com.example.wellness.ui.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
    onclickPerformLogin: () -> Unit,
) {
    val authUiState = viewModel.authState.observeAsState()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var emailIsValidated by rememberSaveable { mutableStateOf(false) }
    var passwordIsValidated by rememberSaveable { mutableStateOf(false) }
    val emailSource = remember { MutableInteractionSource() }
    val passwordSource = remember { MutableInteractionSource() }
    if (emailSource.collectIsPressedAsState().value)
        emailIsValidated = false
    if (passwordSource.collectIsPressedAsState().value)
        passwordIsValidated = false

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
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
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.displayMedium,
            )
            Spacer(modifier = Modifier.padding(PaddingValues(12.dp)))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it; emailIsValidated = false },
                label = {
                    Text( text = stringResource(R.string.email_label) )
                },
                shape = RoundedCornerShape(20.dp),
                colors = textFieldColors,
                interactionSource = emailSource,
                isError = emailIsValidated && !viewModel.validateEmail(email)
            )
            Spacer(modifier = Modifier.padding(PaddingValues(8.dp)))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; passwordIsValidated = false },
                label = {
                    Text( text = stringResource(R.string.password_label) )
                },
                shape = RoundedCornerShape(20.dp),
                colors = textFieldColors,
                visualTransformation =
                    if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description =
                        if (passwordVisible) stringResource(R.string.hide_password)
                        else stringResource(R.string.show_password)

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                },
                interactionSource = passwordSource,
                isError = passwordIsValidated && !viewModel.validatePassword(password)
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
                    viewModel.signIn(email, password)
                    emailIsValidated = true
                    passwordIsValidated = true
                },
                enabled = email.isNotEmpty() && password.isNotEmpty(),
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