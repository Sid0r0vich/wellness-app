package com.example.wellness.data

import android.util.Log
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface UserInfoRepository {
    fun getUserStream(userId: String): Flow<UserInfo?>
    fun insertUser(userInfo: UserInfo, userId: String)
    fun deleteUser(userInfo: UserInfo)
    fun updateUser(userInfo: UserInfo)
}

class UserInfoMockRepository: UserInfoRepository {
    override fun getUserStream(userId: String): Flow<UserInfo?> {
        return flow { emit(MockUser.user) }
    }
    override fun insertUser(userInfo: UserInfo, userId: String) {}
    override fun deleteUser(userInfo: UserInfo) {}
    override fun updateUser(userInfo: UserInfo) {}
}

class UserInfoFirebaseRepository(db: UserDatabase): UserInfoRepository {
    private val collection = db.getDatabase().collection(COLLECTION_NAME)

    override fun getUserStream(userId: String): Flow<UserInfo?> {
        return collection.document(userId)
            .snapshots()
            .map { querySnapshot -> querySnapshot.toObject(UserInfo::class.java) }
    }

    override fun insertUser(userInfo: UserInfo, userId: String) {
        val firebaseData = userInfo.toFirebase()

        collection.document(userId).set(firebaseData)
            .addOnSuccessListener {
                Log.d("UserInfoLog", "Insert user: ${userInfo.name}")
            }
            .addOnFailureListener {
                Log.d("UserInfoLog", "Failure to insert user: ${userInfo.name}")
            }
    }
    override fun deleteUser(userInfo: UserInfo) {}
    override fun updateUser(userInfo: UserInfo) {}

    companion object {
        const val COLLECTION_NAME = "user"

        private fun UserInfo.toFirebase(): Map<String, Any> {
            return mapOf(
                "name" to this.name,
                "email" to this.email,
                "password" to this.password,
                "sex" to this.sex.toString(),
                "age" to this.age
            )
        }
    }
}
