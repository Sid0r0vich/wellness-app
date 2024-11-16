package com.example.wellness.utils

import com.example.wellness.auth.AuthData

object DataValidator {
    enum class Status {
        SUCCESS,
        INVALID_EMAIL_FORMAT,
        INVALID_PASSWORD_FORMAT
    }

    fun validateEmailFormat(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePasswordFormat(password: String): Boolean {
        return password.length >= MIN_PASSWORD_LENGTH
    }

    fun validateAuthDataWithStatus(authData: AuthData): Status {
        if (!validateEmailFormat(authData.email)) {
            return Status.INVALID_EMAIL_FORMAT
        }
        if (!validatePasswordFormat(authData.password)) {
            return Status.INVALID_PASSWORD_FORMAT
        }

        return Status.SUCCESS
    }

    fun validateAuthData(authData: AuthData): Boolean =
        validateAuthDataWithStatus(authData) == Status.SUCCESS

    private const val MIN_PASSWORD_LENGTH = 6
}