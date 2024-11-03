package com.example.wellness.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class UserInfo(
    val name: String,
)

interface UserInfoRepository {
    fun getUserInfo(userId: Int): Flow<UserInfo>
}

class UserInfoMockRepository: UserInfoRepository {
    override fun getUserInfo(userId: Int): Flow<UserInfo> {
        return flow { emit(UserInfo("Sharikov")) }
    }
}