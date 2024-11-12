package com.example.wellness.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.wellness.auth.AuthState

interface AppContainer {
    val userInfoRepository: UserInfoRepository
}

class WellnessAppContainer(private val context: Context) : AppContainer {
    override val userInfoRepository: UserInfoRepository by lazy {
        UserInfoFirebaseRepository(UserDatabase())
    }

    val authState: MutableLiveData<AuthState> = MutableLiveData()
}
