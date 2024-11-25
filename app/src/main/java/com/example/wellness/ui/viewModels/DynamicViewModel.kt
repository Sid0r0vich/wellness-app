package com.example.wellness.ui.viewModels

import com.example.wellness.auth.Auth
import com.example.wellness.data.UserInfoRepository

class DynamicViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : UserViewModel(auth, userInfoRepository) {

    // private val profile = savedStateHandle.toRoute<Profile>() TODO
}