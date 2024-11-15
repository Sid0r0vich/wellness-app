package com.example.wellness.ui.viewModels

import com.example.wellness.auth.Auth
import com.example.wellness.data.UserInfoRepository

class ProfileViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : UserViewModel(auth, userInfoRepository) {
    val authState = auth.authState
    val authStateFlow = auth.authStateFlow
    fun signOut() = auth.signOut()
}