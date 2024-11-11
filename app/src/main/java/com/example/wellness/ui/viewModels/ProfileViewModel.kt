package com.example.wellness.ui.viewModels

import androidx.lifecycle.MutableLiveData
import com.example.wellness.auth.AuthState

class ProfileViewModel(
    authState: MutableLiveData<AuthState>
) : AuthViewModel(authState) {
    fun signOut() {
        auth.signOut()
        authState.value = AuthState.Unauthenticated
    }
}