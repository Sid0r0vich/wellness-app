package com.example.wellness.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.wellness.auth.Auth
import com.example.wellness.auth.AuthData
import com.example.wellness.auth.AuthStatus
import com.example.wellness.auth.EnterStepsUiState
import com.example.wellness.data.UserInfo
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.utils.DataValidator
import com.example.wellness.utils.toAuthStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterStepsViewModel @Inject constructor(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    val authState = auth.authState
    val uiState: EnterStepsUiState = EnterStepsUiState()

    fun validateCredentials(): Boolean {
        return false
    }

    fun signUp(userInfo: UserInfo, onComplete: (AuthStatus) -> Unit = {}) {
        val authData = AuthData(userInfo.email, userInfo.password)
        DataValidator.validateLoginDataWithStatus(authData)
            .toAuthStatus()
            .also { if (it != AuthStatus.SUCCESS) { onComplete(it); return@signUp } }

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