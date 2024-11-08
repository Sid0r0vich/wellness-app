package com.example.wellness.ui.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class AuthUiState(
    email: MutableState<String> = mutableStateOf(""),
    password: MutableState<String> = mutableStateOf(""),
    passwordVisible: MutableState<Boolean> = mutableStateOf(false),
    emailIsValidated: MutableState<Boolean> = mutableStateOf(false),
    passwordIsValidated: MutableState<Boolean> = mutableStateOf(false),
    var emailSource: MutableInteractionSource = MutableInteractionSource(),
    var passwordSource: MutableInteractionSource = MutableInteractionSource()
) {
    var email by email
    var password by password
    var passwordVisible by passwordVisible
    var emailIsValidated by emailIsValidated
    var passwordIsValidated by passwordIsValidated
}

@Composable
fun rememberAuthUiState(): AuthUiState {
    return AuthUiState(
        email = rememberSaveable { mutableStateOf("") },
        password = rememberSaveable { mutableStateOf("") },
        passwordVisible = rememberSaveable { mutableStateOf(false) },
        emailIsValidated = rememberSaveable { mutableStateOf(false) },
        passwordIsValidated = rememberSaveable { mutableStateOf(false) },
        emailSource = remember { MutableInteractionSource() },
        passwordSource = remember { MutableInteractionSource() }
    )
}