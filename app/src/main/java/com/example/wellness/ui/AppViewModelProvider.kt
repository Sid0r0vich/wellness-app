package com.example.wellness.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wellness.WellnessApplication
import com.example.wellness.data.WellnessAppContainer
import com.example.wellness.ui.screens.AuthState
import com.example.wellness.ui.screens.HomeViewModel
import com.example.wellness.ui.screens.LoginViewModel
import com.example.wellness.ui.screens.ProfileViewModel
import com.example.wellness.ui.screens.RegisterViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                SavedStateHandle(),
                getApplication().container.userInfoRepository
            )
        }

        initializer {
            LoginViewModel(getAuthState())
        }

        initializer {
            RegisterViewModel(getAuthState())
        }

        initializer {
            ProfileViewModel(getAuthState())
        }
    }
}

fun CreationExtras.getApplication(): WellnessApplication =
    (this[APPLICATION_KEY] as WellnessApplication)

fun CreationExtras.getAuthState(): MutableLiveData<AuthState> =
    (getApplication().container as WellnessAppContainer).authState