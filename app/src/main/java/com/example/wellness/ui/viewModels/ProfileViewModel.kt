package com.example.wellness.ui.viewModels

import androidx.lifecycle.MutableLiveData
import com.example.wellness.auth.AuthState
import com.example.wellness.data.UserInfoRepository

class ProfileViewModel(
    authState: MutableLiveData<AuthState>,
    private val userInfoRepository: UserInfoRepository
) : AuthViewModel(authState, userInfoRepository) {
    fun signOut() {
        auth.signOut()
        authState.value = AuthState.Unauthenticated
    }
}