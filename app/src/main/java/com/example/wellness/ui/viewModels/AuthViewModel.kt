package com.example.wellness.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellness.auth.AuthState
import com.example.wellness.auth.AuthUiState
import com.example.wellness.auth.RegisterUiState
import com.example.wellness.auth.Sex
import com.example.wellness.data.UserInfo
import com.example.wellness.data.UserInfoRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

abstract class AuthViewModel(
    authState: MutableLiveData<AuthState>,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    protected  val auth: FirebaseAuth = FirebaseAuth.getInstance()
    var authState: MutableLiveData<AuthState> = authState
        protected set

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser == null)
            authState.value = AuthState.Unauthenticated
        else authState.value = AuthState.Authenticated
    }
}

open class LoginViewModel(
    authState: MutableLiveData<AuthState>,
    private val userInfoRepository: UserInfoRepository
) : AuthViewModel(authState, userInfoRepository) {
    open val uiState: AuthUiState = AuthUiState()

    protected  fun validateEmailAndPassword(
        email: String,
        password: String
    ): Boolean {
        if (!DataValidator.validateEmailFormat(email)) {
            authState.value = AuthState.Error(EMAIL_VALIDATION_EXCEPTION)
            return false
        }
        if (!DataValidator.validatePasswordFormat(password)) {
            authState.value = AuthState.Error(PASSWORD_VALIDATION_EXCEPTION)
            return false
        }

        return true
    }

    fun signIn(
        email: String,
        password: String,
    ) {
        authState.value = AuthState.Loading
        if (!validateEmailAndPassword(email, password)) return

        auth.signInWithEmailAndPassword(email, password)
            .addAuthenticateListener()
    }

    protected fun <TResult> Task<TResult>.addAuthenticateListener() {
        this.addOnCompleteListener { task ->
            if (task.isSuccessful)
                authState.value = AuthState.Authenticated
            else
                authState.value = AuthState.Error(
                    task.exception?.message ?: UNKNOWN_EXCEPTION
                )
        }
    }

    companion object {
        protected  const val UNKNOWN_EXCEPTION = "Something went wrong"
        protected  const val EMAIL_VALIDATION_EXCEPTION = "Wrong email!"
        protected  const val PASSWORD_VALIDATION_EXCEPTION = "Short password!"
    }
}

class RegisterViewModel(
    authState: MutableLiveData<AuthState>,
    private val userInfoRepository: UserInfoRepository
) : LoginViewModel(authState, userInfoRepository) {
    override val uiState: RegisterUiState = RegisterUiState()

    fun signUp(
        name: String,
        email: String,
        password: String,
        sex: Sex,
        age: Int
    ) {
        authState.value = AuthState.Loading
        if (!validateEmailAndPassword(email, password)) return

        auth.createUserWithEmailAndPassword(email, password)
            .addAuthenticateListener()

        viewModelScope.launch {
            userInfoRepository.insertUser(
                UserInfo(
                    name = name,
                    email = email,
                    password = password,
                    sex = sex,
                    age = age
                )
            )
        }
    }
}

object DataValidator {
    fun validateEmailFormat(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePasswordFormat(password: String): Boolean {
        return password.length >= 6
    }
}