package com.example.wellness

import android.app.Application
import com.example.wellness.data.AppContainer
import com.example.wellness.data.WellnessAppContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WellnessApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = WellnessAppContainer(this)
    }
}