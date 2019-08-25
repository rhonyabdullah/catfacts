package com.example.catfacts

import com.example.catfacts.di.DependencyInjection

class AndroidTestApplication : Application() {

    override val di = TestDependencyInjection
}

object TestDependencyInjection : DependencyInjection {

    override val catFactViewModel = AndroidTestViewModel()
}
