package com.example.wellness.di

import com.example.wellness.auth.Auth
import com.example.wellness.auth.FirebaseAuth
import com.example.wellness.data.IndicatorMockRepository
import com.example.wellness.data.IndicatorRepository
import com.example.wellness.data.UserInfoFirebaseRepository
import com.example.wellness.data.UserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun bindAuth(impl: FirebaseAuth): Auth

    @Binds
    @Singleton
    abstract fun bindUserInfoRepository(impl: UserInfoFirebaseRepository): UserInfoRepository

    @Binds
    @Singleton
    abstract fun bindIndicatorRepository(impl: IndicatorMockRepository): IndicatorRepository

}