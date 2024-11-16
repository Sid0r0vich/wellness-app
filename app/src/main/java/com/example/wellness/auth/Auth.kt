package com.example.wellness.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface Auth {
    val authState: AuthState
    val authStateFlow: StateFlow<AuthState>
    val userId: StateFlow<String?>

    fun signIn(authData: AuthData, onSignIn: (AuthStatus) -> Unit = {})
    fun signUp(authData: AuthData, onSignUp: (AuthStatus) -> Unit = {})
    fun signOut()
}

class FirebaseAuth : Auth {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState: MutableStateFlow<AuthState> =
        MutableStateFlow(AuthState.Unauthenticated)
    override val authStateFlow: StateFlow<AuthState>
        get() = _authState
    override var authState: AuthState
        get() = _authState.value
        private set(value) {
            _authState.value = value
        }

    private val _userId: MutableStateFlow<String?> =
        MutableStateFlow(firebaseAuth.currentUser?.uid)
    override val userId: StateFlow<String?>
        get() = _userId

    private val authErrorHandler = AuthErrorHandler()

    init {
        checkAuthStatus()
        firebaseAuth.addAuthStateListener {
            _userId.value = firebaseAuth.currentUser?.uid
        }
    }

    private fun checkAuthStatus() {
        authState =
            if (_userId.value == null) AuthState.Unauthenticated
            else AuthState.Authenticated
    }

    override fun signIn(
        authData: AuthData,
        onSignIn: (AuthStatus) -> Unit,
    ) {
        authState = AuthState.Loading
        firebaseAuth.signInWithEmailAndPassword(
            authData.email,
            authData.password
        ).addAuthenticateListener(onSignIn)
    }

    override fun signUp(
        authData: AuthData,
        onSignUp: (AuthStatus) -> Unit,
    ) {
        authState = AuthState.Loading
        firebaseAuth.createUserWithEmailAndPassword(
            authData.email,
            authData.password
        ).addAuthenticateListener(onSignUp)
    }

    override fun signOut() {
        authState = AuthState.Loading
        firebaseAuth.signOut()
        authState = AuthState.Unauthenticated
    }

    private fun Task<AuthResult>.addAuthenticateListener(
        onAuthenticate: (AuthStatus) -> Unit = {},
    ) {
        this.addOnCompleteListener { task ->
            var status = AuthStatus.SUCCESS

            if (task.isSuccessful) {
                authState = AuthState.Authenticated
            }
            else {
                authState = AuthState.Error(
                    task.exception?.message ?: UNKNOWN_EXCEPTION
                )
                status = authErrorHandler.handleAuthError(task.exception)
            }

            onAuthenticate(status)
        }
    }

    companion object {
        private  const val UNKNOWN_EXCEPTION = "Something went wrong"
    }
}

class AuthErrorHandler {
    fun handleAuthError(exception: Exception?): AuthStatus {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> AuthStatus.USER_NOT_FOUND
            is FirebaseAuthUserCollisionException -> AuthStatus.EMAIL_COLLISION
            is FirebaseAuthWeakPasswordException -> AuthStatus.WEAK_PASSWORD
            is FirebaseAuthInvalidCredentialsException -> AuthStatus.INVALID_CREDENTIALS
            else -> AuthStatus.UNKNOWN_ERROR
        }
    }
}