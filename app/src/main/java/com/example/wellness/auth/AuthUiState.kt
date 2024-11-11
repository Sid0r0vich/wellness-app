package com.example.wellness.auth

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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

class RegisterUiState(
    selectedSex: MutableState<Sex> = mutableStateOf(Sex.Man),
    age: MutableState<Int> = mutableIntStateOf(DEFAULT_AGE)
) : AuthUiState() {
    var selectedSex by selectedSex
    var age by age

    companion object {
        private const val DEFAULT_AGE = 18
        val AGE_RANGE = 14..120
    }
}