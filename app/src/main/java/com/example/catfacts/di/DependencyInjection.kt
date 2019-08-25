package com.example.catfacts.di

import com.example.catfacts.catfact.CatFactAction
import com.example.catfacts.catfact.CatFactState
import com.example.catfacts.catfact.CatFactUseCaseImpl
import com.example.catfacts.catfact.CatFactViewModel
import com.example.catfacts.model.CatFactRepository
import com.example.catfacts.model.api.CatService
import com.squareup.moshi.Moshi
import com.ww.roxie.BaseViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface DependencyInjection {
    val catFactViewModel: BaseViewModel<CatFactAction, CatFactState>
}

class DependencyInjectionImpl : DependencyInjection {

    override lateinit var catFactViewModel: BaseViewModel<CatFactAction, CatFactState>

    init {
        val apiBaseUrl = "https://cat-fact.herokuapp.com"
        val moshi = Moshi.Builder().build()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        val catService = retrofit.create(CatService::class.java)

        val catFactRepository = CatFactRepository(catService)

        val getCatFactUseCase = CatFactUseCaseImpl(catFactRepository)

        catFactViewModel = CatFactViewModel(CatFactState(activity = false), getCatFactUseCase)
    }
}
