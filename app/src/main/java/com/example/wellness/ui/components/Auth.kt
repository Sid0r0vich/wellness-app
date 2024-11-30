package com.example.wellness.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.wellness.R
import com.example.wellness.auth.AuthState
import com.example.wellness.auth.AuthUiState
import com.example.wellness.utils.DataValidator

@Composable
fun EmailField(
    value: String,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource,
    isError: Boolean
) {
    WellnessTextFiled(
        value = value,
        onValueChange = onValueChange,
        textId = R.string.email_label,
        interactionSource = interactionSource,
        isError = isError
    )
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    interactionSource: MutableInteractionSource,
    isError: Boolean,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    WellnessTextFiled(
        value = value,
        onValueChange = onValueChange,
        textId = R.string.password_label,
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
        interactionSource = interactionSource,
        isError = isError
    )
}

@Composable
fun AuthEmailField(uiState: AuthUiState) {
    EmailField(
        value = uiState.email,
        onValueChange = { uiState.email = it; uiState.emailIsValidated = false },
        interactionSource = uiState.emailSource,
        isError = uiState.emailIsValidated && !DataValidator.validateEmailFormat(uiState.email)
    )
}

@Composable
fun AuthPasswordField(uiState: AuthUiState) {
    PasswordField(
        value = uiState.password,
        onValueChange = { uiState.password = it; uiState.passwordIsValidated = false },
        interactionSource = uiState.passwordSource,
        isError = uiState.passwordIsValidated && !DataValidator.validatePasswordFormat(uiState.password)
    )
}

@Composable
fun AuthFieldsInvalidation(
    uiState: AuthUiState
) {
    if (uiState.emailSource.collectIsPressedAsStateValue())
        uiState.emailIsValidated = false
    if (uiState.passwordSource.collectIsPressedAsStateValue())
        uiState.passwordIsValidated = false
}

@Composable
fun MutableInteractionSource.collectIsPressedAsStateValue(): Boolean {
    return this.collectIsPressedAsState().value
}

@Composable
fun Header(textId: Int) {
    Text(
        text = stringResource(textId),
        style = MaterialTheme.typography.displayMedium,
    )
}

@Composable
fun AuthButton(
    authState: AuthState,
    uiState: AuthUiState,
    textId: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = {
            onClick()
            uiState.emailIsValidated = true
            uiState.passwordIsValidated = true
        },
        enabled =
            uiState.email.isNotEmpty() &&
            uiState.password.isNotEmpty() &&
            authState != AuthState.Loading,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(textId))
    }
}