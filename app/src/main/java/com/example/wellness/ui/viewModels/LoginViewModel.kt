package com.example.wellness.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.wellness.auth.Auth
import com.example.wellness.auth.AuthData
import com.example.wellness.auth.AuthStatus
import com.example.wellness.auth.AuthUiState
import com.example.wellness.utils.DataValidator

open class LoginViewModel(
    private val auth: Auth,
) : ViewModel() {
    val authState = auth.authState
    val authStateFlow = auth.authStateFlow
    open val uiState: AuthUiState = AuthUiState()

    fun signIn(authData: AuthData, onComplete: (AuthStatus) -> Unit = {}) {
        val loginStatus = when(DataValidator.validateAuthDataWithStatus(authData)) {
            DataValidator.Status.SUCCESS -> AuthStatus.SUCCESS
            DataValidator.Status.INVALID_EMAIL_FORMAT -> AuthStatus.INVALID_EMAIL_FORMAT
            DataValidator.Status.INVALID_PASSWORD_FORMAT -> AuthStatus.INVALID_PASSWORD_FORMAT
        }

        if (loginStatus == AuthStatus.SUCCESS)
            auth.signIn(authData, onComplete)
        else onComplete(loginStatus)
    }
}