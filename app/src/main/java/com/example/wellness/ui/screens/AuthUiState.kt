package com.example.wellness.ui.screens

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class AuthUiState(
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

sealed class Sex {
    data object Man: Sex()
    data object Woman: Sex()
}

class RegisterUiState: AuthUiState() {
    val selectedSex: MutableState<Sex> = mutableStateOf(Sex.Man)
    val age: MutableState<Int> = mutableStateOf(DEFAULT_AGE)

    companion object {
        private const val DEFAULT_AGE = 18
        val AGE_RANGE = 14..120
    }
}