package com.example.wellness.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.wellness.auth.Auth
import com.example.wellness.auth.AuthUiState
import com.example.wellness.auth.RegisterUiState
import com.example.wellness.data.AuthData
import com.example.wellness.data.UserInfo
import com.example.wellness.data.UserInfoRepository

open class AuthViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    val authState = auth.authState
    val authLiveData = auth.authLiveData
}

open class LoginViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : AuthViewModel(auth, userInfoRepository) {
    open val uiState: AuthUiState = AuthUiState()

    fun signIn(authData: AuthData) {
        if (!DataValidator.validateAuthData(authData)) return

        auth.signIn(authData)
    }
}

class RegisterViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : AuthViewModel(auth, userInfoRepository) {
    val uiState: RegisterUiState = RegisterUiState()

    fun signUp(userInfo: UserInfo) {
        val authData = AuthData(userInfo.email, userInfo.password)
        if (!DataValidator.validateAuthData(authData)) return

        auth.signUp(authData)

        val userId: String = auth.getUserId() ?: return
        userInfoRepository.insertUser(userInfo, userId)
    }
}

object DataValidator {
    enum class Status {
        SUCCESS,
        EMAIL_VALIDATION_EXCEPTION,
        PASSWORD_VALIDATION_EXCEPTION
    }

    fun validateEmailFormat(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePasswordFormat(password: String): Boolean {
        return password.length >= 6
    }

    fun validateAuthDataWithStatus(authData: AuthData): Status {
        if (!validateEmailFormat(authData.email)) {
            return Status.EMAIL_VALIDATION_EXCEPTION
        }
        if (!validatePasswordFormat(authData.password)) {
            return Status.PASSWORD_VALIDATION_EXCEPTION
        }

        return Status.SUCCESS
    }

    fun validateAuthData(authData: AuthData): Boolean =
        validateAuthDataWithStatus(authData) == Status.SUCCESS

    val statusList: List<String> = listOf(
        "Success!",
        "Wrong email!",
        "Short password!"
    )
}