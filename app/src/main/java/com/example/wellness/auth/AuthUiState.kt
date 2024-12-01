package com.example.wellness.auth

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.wellness.data.Sex

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


class EnterStepsUiState(
    name: MutableState<String> = mutableStateOf(""),
    selectedSex: MutableState<Sex> = mutableStateOf(Sex.Man),
    age: MutableState<Int> = mutableIntStateOf(DEFAULT_AGE),
    height: MutableState<Int> = mutableIntStateOf(DEFAULT_HEIGHT),
    weight: MutableState<Int> = mutableIntStateOf(DEFAULT_WEIGHT),
    nameIsValidated: MutableState<Boolean> = mutableStateOf(false),
    var nameSource: MutableInteractionSource = MutableInteractionSource()
) : AuthUiState() {
    var name by name
    var selectedSex by selectedSex
    var age by age
    var height by height
    var weight by weight
    var nameIsValidated by nameIsValidated

    companion object {
        private const val DEFAULT_AGE = 18
        private const val DEFAULT_HEIGHT = 175
        private const val DEFAULT_WEIGHT = 70

        val AGE_RANGE = 14..99
        val HEIGHT_RANGE = 120..210
        val WEIGHT_RANGE = 40..160
    }
}
