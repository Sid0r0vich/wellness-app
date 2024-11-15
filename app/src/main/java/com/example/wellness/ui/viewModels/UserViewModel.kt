package com.example.wellness.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellness.auth.Auth
import com.example.wellness.data.AnonymousUser
import com.example.wellness.data.NotFoundUser
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.data.UserUiInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class UserViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    private var userUiInfo: StateFlow<UserUiInfo> = auth.userId
        .flatMapLatest { userId ->
            userId?.let {
                userInfoRepository
                    .getUserStream(userId)
                    .map { userInfo ->
                        userInfo?.toUserUiInfo() ?: NotFoundUser.user
                    }
            } ?: flow { emit(AnonymousUser.user) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = NotFoundUser.user
        )

    val uiState: StateFlow<UiState> =
        userUiInfo
            .map { UiState(it.name) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UiState("")
            )

    data class UiState(
        val userName: String
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}