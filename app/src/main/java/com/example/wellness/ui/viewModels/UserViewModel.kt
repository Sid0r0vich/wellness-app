package com.example.wellness.ui.viewModels

import androidx.lifecycle.ViewModel
import com.example.wellness.auth.Auth
import com.example.wellness.data.AnonymousUser
import com.example.wellness.data.NotFoundUser
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.data.UserUiInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class UserViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    protected val userUiInfo: Flow<UserUiInfo> = auth.getUserId()?.let {
        userInfoRepository.getUserStream(it).map {
                userInfo -> userInfo?.toUserUiInfo() ?: NotFoundUser.user
        }
    } ?: flow { emit(AnonymousUser.user) }

    data class UiState(
        val userName: String
    )
}