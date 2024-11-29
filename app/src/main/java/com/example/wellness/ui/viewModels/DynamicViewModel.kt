package com.example.wellness.ui.viewModels

import androidx.lifecycle.viewModelScope
import com.example.wellness.auth.Auth
import com.example.wellness.data.Indicator
import com.example.wellness.data.IndicatorRepository
import com.example.wellness.data.InitIndicator
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.ui.viewModels.UserViewModel.Companion.TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DynamicViewModel @Inject constructor(
    auth: Auth,
    userInfoRepository: UserInfoRepository,
    private val indicatorRepository: IndicatorRepository
) : UserViewModel(auth, userInfoRepository) {
    fun getIndicatorStateFlows(): List<StateFlow<Indicator>> =
        indicatorRepository.getIndicatorStateFlows(viewModelScope)
}

fun IndicatorRepository.getIndicatorStateFlows(scope: CoroutineScope): List<StateFlow<Indicator>> =
    this.getStream().map { flow ->
        flow.stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = InitIndicator.indicator
        )
    }