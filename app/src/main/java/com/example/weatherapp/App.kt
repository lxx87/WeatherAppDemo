package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.extension.DelegatesExt

class App : Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}