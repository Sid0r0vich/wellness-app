package com.example.wellness.data

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UserInfoRepository {
    fun getUserStream(userId: Int): Flow<UserInfo?>
    suspend fun insertUser(userInfo: UserInfo)
    suspend fun deleteUser(userInfo: UserInfo)
    suspend fun updateUser(userInfo: UserInfo)
}

class UserInfoMockRepository: UserInfoRepository {
    override fun getUserStream(userId: Int): Flow<UserInfo?> {
        return flow { emit(MockUser.user) }
    }
    override suspend fun insertUser(userInfo: UserInfo) {}
    override suspend fun deleteUser(userInfo: UserInfo) {}
    override suspend fun updateUser(userInfo: UserInfo) {}
}

class UserInfoFirebaseRepository(private val db: UserDatabase): UserInfoRepository {
    private val collection = db.getDatabase().collection(COLLECTION_NAME)

    override fun getUserStream(userId: Int): Flow<UserInfo?> {
        return flow { emit(MockUser.user) }
    }

    override suspend fun insertUser(userInfo: UserInfo) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val userMap = hashMapOf(
            "name" to userInfo.name,
            "email" to userInfo.email,
            "password" to userInfo.password,
            "sex" to userInfo.sex.toString(),
            "age" to userInfo.age
        )

        collection.document(userId).set(userMap)
            .addOnSuccessListener {
                Log.d("UserInfoLog", "Insert user: ${userInfo.name}")
            }
            .addOnFailureListener {
                Log.d("UserInfoLog", "Failure to insert user: ${userInfo.name}")
            }
    }
    override suspend fun deleteUser(userInfo: UserInfo) {}
    override suspend fun updateUser(userInfo: UserInfo) {}

    companion object {
        const val COLLECTION_NAME = "user"
    }
}