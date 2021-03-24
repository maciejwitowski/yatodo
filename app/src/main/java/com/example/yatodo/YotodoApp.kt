package com.example.yatodo

import android.app.Application
import com.example.yatodo.di.AppContainer
import com.example.yatodo.di.AppContainerImpl

class YotodoApp : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}