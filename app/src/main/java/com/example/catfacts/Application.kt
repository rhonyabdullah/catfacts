package com.example.catfacts

import android.app.Application
import com.example.catfacts.di.DependencyInjection
import com.ww.roxie.Roxie

class Application : Application() {

    val di by lazy {
        DependencyInjection()
    }

    override fun onCreate() {
        super.onCreate()
        Roxie.enableLogging()
    }
}