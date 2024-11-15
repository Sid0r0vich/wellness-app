package com.example.wellness.ui.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.wellness.auth.Auth
import com.example.wellness.data.UserInfoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository
) : UserViewModel(auth, userInfoRepository) {

    // private val profile = savedStateHandle.toRoute<Profile>() TODO

    val uiState: StateFlow<UiState> =
        userUiInfo
            .map { UiState(it?.name ?: "User") }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UiState("User")
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val userName: String
)