package com.example.wellness.data

import android.content.Context
import com.example.wellness.auth.FirebaseAuth

interface AppContainer {
    val userInfoRepository: UserInfoRepository
}

class WellnessAppContainer(private val context: Context) : AppContainer {
    override val userInfoRepository: UserInfoRepository by lazy {
        UserInfoFirebaseRepository(UserDatabase())
    }

    val auth: FirebaseAuth = FirebaseAuth()
}
