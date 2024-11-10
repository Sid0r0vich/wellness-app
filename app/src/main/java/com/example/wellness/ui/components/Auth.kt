package com.example.wellness.ui.components

import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.wellness.R
import com.example.wellness.ui.screens.AuthState

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
fun AuthenticationTrigger(
    authState: State<AuthState?>,
    onPerformAuth: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> onPerformAuth()
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }
}

@Composable
fun UnauthenticatedTrigger(
    authState: State<AuthState?>,
    onUnauthenticated: () -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Unauthenticated -> onUnauthenticated()
            else -> Unit
        }
    }
}

@Composable
fun MutableInteractionSource.collectIsPressedAsStateValue(): Boolean {
    return this.collectIsPressedAsState().value
}