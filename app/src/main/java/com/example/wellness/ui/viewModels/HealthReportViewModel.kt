package com.example.wellness.ui.viewModels

import androidx.lifecycle.viewModelScope
import com.example.wellness.auth.Auth
import com.example.wellness.data.Indicator
import com.example.wellness.data.IndicatorRepository
import com.example.wellness.data.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HealthReportViewModel @Inject constructor(
    auth: Auth,
    userInfoRepository: UserInfoRepository,
    private val indicatorRepository: IndicatorRepository

) : UserViewModel(auth, userInfoRepository) {
    fun getIndicatorStateFlows(): List<StateFlow<Indicator>> =
        indicatorRepository.getIndicatorStateFlows(viewModelScope)
}