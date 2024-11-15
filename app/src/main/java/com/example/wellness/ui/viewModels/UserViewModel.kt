package com.example.wellness.ui.viewModels

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wellness.auth.Auth
import com.example.wellness.data.AnonymousUser
import com.example.wellness.data.NotFoundUser
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.data.UserUiInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

abstract class UserViewModel(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {
    private var userUiInfo: Flow<UserUiInfo> = MutableStateFlow(NotFoundUser.user)

    init {
        Observer<String?> {
            userUiInfo = auth.userId.value?.let { userId ->
                userInfoRepository
                    .getUserStream(userId)
                    .map { userInfo ->
                        userInfo?.toUserUiInfo() ?: NotFoundUser.user
                    }
            } ?: flow { emit(AnonymousUser.user) }
        }.onChanged(auth.userId.value)
    }

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