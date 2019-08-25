package com.example.catfacts

import android.app.Application
import android.content.Context
import com.example.catfacts.di.DependencyInjection
import com.example.catfacts.di.DependencyInjectionImpl
import com.ww.roxie.Roxie

open class Application : Application() {

    open val di: DependencyInjection by lazy {
        DependencyInjectionImpl()
    }

    override fun onCreate() {
        super.onCreate()
        Roxie.enableLogging()
    }
}

val Context.di: DependencyInjection
    get() = (this.applicationContext as com.example.catfacts.Application).di
