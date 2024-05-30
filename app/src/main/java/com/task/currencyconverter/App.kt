package com.task.currencyconverter

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    init {
        application = this
    }

    companion object{
        private lateinit var application: App
        fun getApplicationContext(): Context = application.applicationContext
    }
}