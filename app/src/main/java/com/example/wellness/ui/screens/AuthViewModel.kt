package com.example.wellness.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

abstract class AuthViewModel : ViewModel() {
    protected  val auth: FirebaseAuth = FirebaseAuth.getInstance()
    protected  val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    open val uiState = AuthUiState()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser == null)
            _authState.value = AuthState.Unauthenticated
        else _authState.value = AuthState.Authenticated
    }

    fun validateEmailFormat(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePasswordFormat(password: String): Boolean {
        return password.length >= 6
    }

    protected  fun validateEmailAndPassword(
        email: String,
        password: String
    ): Boolean {
        if (!validateEmailFormat(email)) {
            _authState.value = AuthState.Error(EMAIL_VALIDATION_EXCEPTION)
            return false
        }
        if (!validatePasswordFormat(password)) {
            _authState.value = AuthState.Error(PASSWORD_VALIDATION_EXCEPTION)
            return false
        }

        return true
    }

    fun signIn(
        email: String,
        password: String,
    ) {
        _authState.value = AuthState.Loading
        if (!validateEmailAndPassword(email, password)) return

        auth.signInWithEmailAndPassword(email, password)
            .addAuthenticateListener()
    }

    protected fun <TResult> Task<TResult>.addAuthenticateListener() {
        this.addOnCompleteListener { task ->
            if (task.isSuccessful)
                _authState.value = AuthState.Authenticated
            else
                _authState.value = AuthState.Error(
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

class LoginViewModel : AuthViewModel()

class RegisterViewModel : AuthViewModel() {
    override val uiState = RegisterUiState()

    fun signUp(
        email: String,
        password: String,
    ) {
        _authState.value = AuthState.Loading
        if (!validateEmailAndPassword(email, password)) return

        auth.createUserWithEmailAndPassword(email, password)
            .addAuthenticateListener()
    }
}

class SignOutViewModel : AuthViewModel() {
    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    data object Authenticated: AuthState()
    data object Unauthenticated: AuthState()
    data object Loading: AuthState()
    data class Error(val message: String): AuthState()
}