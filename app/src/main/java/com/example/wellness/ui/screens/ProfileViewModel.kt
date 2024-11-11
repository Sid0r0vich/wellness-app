package com.example.wellness.ui.screens

import androidx.lifecycle.MutableLiveData

class ProfileViewModel(
    authState: MutableLiveData<AuthState>
) : AuthViewModel(authState) {
    fun signOut() {
        auth.signOut()
        authState.value = AuthState.Unauthenticated
    }
}