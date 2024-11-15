package com.example.wellness.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.wellness.data.AuthData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

interface Auth {
    val authState: AuthState
    val authLiveData: LiveData<AuthState>

    fun signIn(authData: AuthData)
    fun signUp(authData: AuthData)
    fun signOut()
    fun getUserId(): String?
}

class FirebaseAuth : Auth {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _authState: MutableLiveData<AuthState> = MutableLiveData()
    override val authLiveData: LiveData<AuthState>
        get() = _authState
    override var authState: AuthState
        get() = _authState.value ?: AuthState.Loading
        private set(value) {
            _authState.value = value
        }

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (getUserId() == null)
            authState = AuthState.Unauthenticated
        else authState = AuthState.Authenticated
    }

    override fun signIn(authData: AuthData) {
        authState = AuthState.Loading
        firebaseAuth.signInWithEmailAndPassword(
            authData.email,
            authData.password
        ).addAuthenticateListener()
    }

    override fun signUp(authData: AuthData) {
        authState = AuthState.Loading
        firebaseAuth.createUserWithEmailAndPassword(
            authData.email,
            authData.password
        ).addAuthenticateListener()
    }

    override fun signOut() {
        authState = AuthState.Loading
        firebaseAuth.signOut()
        authState = AuthState.Unauthenticated
    }

    override fun getUserId(): String? = firebaseAuth.currentUser?.uid

    private fun <TResult> Task<TResult>.addAuthenticateListener() {
        this.addOnCompleteListener { task ->
            if (task.isSuccessful)
                authState = AuthState.Authenticated
            else
                authState = AuthState.Error(
                    task.exception?.message ?: UNKNOWN_EXCEPTION
                )
        }
    }

    companion object {
        private  const val UNKNOWN_EXCEPTION = "Something went wrong"
    }
}