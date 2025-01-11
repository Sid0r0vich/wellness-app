package com.example.wellness.ui.viewModels

import android.content.Context
import com.example.wellness.auth.Auth
import com.example.wellness.data.UserInfoRepository
import com.example.wellness.rustore.ReviewManager
import com.example.wellness.rustore.UpdateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: Auth,
    private val userInfoRepository: UserInfoRepository,
    private val workManagerUpdateRepository: UpdateRepository
) : UserViewModel(auth, userInfoRepository) {
    fun signOut() = auth.signOut()

    fun review(context: Context) {
        ReviewManager(context).review()
    }

    fun checkUpdates(context: Context) {
        workManagerUpdateRepository.checkAndUpdates(context)
    }
}