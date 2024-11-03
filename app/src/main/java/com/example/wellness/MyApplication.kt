package com.example.wellness

import android.app.Application
import com.example.wellness.data.AppContainer
import com.example.wellness.data.AppDataContainer

class MyApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}