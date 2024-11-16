package com.example.wellness.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.wellness.auth.Auth
import com.example.wellness.auth.AuthData
import com.example.wellness.auth.AuthStatus
import com.example.wellness.auth.RegisterUiState
import com.example.wellness.data.UserInfo
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.utils.DataValidator

class RegisterViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    val authState = auth.authState
    val authStateFlow = auth.authStateFlow
    val uiState: RegisterUiState = RegisterUiState()

    fun signUp(userInfo: UserInfo, onComplete: (AuthStatus) -> Unit = {}) {
        val authData = AuthData(userInfo.email, userInfo.password)
        if (!DataValidator.validateAuthData(authData)) return

        auth.signUp(authData) { status ->
            if (status == AuthStatus.SUCCESS) {
                auth.userId.value?.let { userId ->
                    userInfoRepository.insertUser(userInfo, userId)
                }
            }
            onComplete(status)
        }
    }
}