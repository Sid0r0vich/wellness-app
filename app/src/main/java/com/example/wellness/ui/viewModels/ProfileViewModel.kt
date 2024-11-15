package com.example.wellness.ui.viewModels

import com.example.wellness.auth.Auth
import com.example.wellness.data.UserInfoRepository

class ProfileViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : AuthViewModel(auth, userInfoRepository) {
    fun signOut() = auth.signOut()
}