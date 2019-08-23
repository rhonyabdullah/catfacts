package com.example.catfacts

import android.app.Application
import android.content.Context
import com.example.catfacts.di.DependencyInjection
import com.ww.roxie.Roxie

class Application : Application() {

    open val di by lazy {
        DependencyInjection()
    }

    override fun onCreate() {
        super.onCreate()
        Roxie.enableLogging()
    }
}

val Context.di: DependencyInjection
    get() = (this.applicationContext as Application).di