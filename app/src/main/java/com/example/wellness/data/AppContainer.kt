package com.example.wellness.data

import android.content.Context

interface AppContainer {
    val userInfoRepository: UserInfoRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userInfoRepository: UserInfoRepository by lazy {
        UserInfoMockRepository()
    }
}
