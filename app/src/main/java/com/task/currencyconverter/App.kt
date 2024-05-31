package com.task.currencyconverter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    init {
        application = this
    }

    companion object{
        private lateinit var application: App
    }
}