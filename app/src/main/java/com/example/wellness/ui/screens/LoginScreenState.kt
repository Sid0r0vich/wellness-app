package com.example.wellness.ui.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AuthUiState(
    email: MutableState<String> = mutableStateOf(""),
    password: MutableState<String> = mutableStateOf(""),
    emailIsValidated: MutableState<Boolean> = mutableStateOf(false),
    passwordIsValidated: MutableState<Boolean> = mutableStateOf(false),
    var emailSource: MutableInteractionSource = MutableInteractionSource(),
    var passwordSource: MutableInteractionSource = MutableInteractionSource()
) {
    var email by email
    var password by password
    var emailIsValidated by emailIsValidated
    var passwordIsValidated by passwordIsValidated
}