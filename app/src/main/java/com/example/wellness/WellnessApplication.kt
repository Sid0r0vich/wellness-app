package com.example.wellness

import android.app.Application
import com.example.wellness.data.AppContainer
import com.example.wellness.data.WellnessAppContainer

class WellnessApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = WellnessAppContainer(this)
    }
}