package com.example.wellness.ui.viewModels

import com.example.wellness.auth.Auth
import com.example.wellness.data.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
//    savedStateHandle: SavedStateHandle, TODO
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : UserViewModel(auth, userInfoRepository) {

    // private val profile = savedStateHandle.toRoute<Profile>() TODO
}