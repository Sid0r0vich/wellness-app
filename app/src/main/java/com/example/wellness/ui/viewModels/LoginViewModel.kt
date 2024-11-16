package com.example.wellness.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.wellness.auth.Auth
import com.example.wellness.auth.AuthData
import com.example.wellness.auth.AuthStatus
import com.example.wellness.auth.AuthUiState
import com.example.wellness.utils.DataValidator
import com.example.wellness.utils.toAuthStatus

open class LoginViewModel(
    private val auth: Auth,
) : ViewModel() {
    val authState = auth.authState
    val authStateFlow = auth.authStateFlow
    open val uiState: AuthUiState = AuthUiState()

    fun signIn(authData: AuthData, onComplete: (AuthStatus) -> Unit = {}) {
        DataValidator.validateAuthDataWithStatus(authData)
            .toAuthStatus()
            .also { if (it != AuthStatus.SUCCESS) { onComplete(it); return@signIn } }

        auth.signIn(authData, onComplete)
    }
}