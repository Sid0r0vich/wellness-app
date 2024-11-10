package com.example.wellness.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wellness.MyApplication
import com.example.wellness.ui.screens.HomeViewModel
import com.example.wellness.ui.screens.LoginViewModel
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
            LoginViewModel()
        }

        initializer {
            RegisterViewModel()
        }
    }
}

fun CreationExtras.getApplication(): MyApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MyApplication)