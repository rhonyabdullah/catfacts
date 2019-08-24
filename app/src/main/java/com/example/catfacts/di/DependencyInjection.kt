package com.example.catfacts.di

import com.example.catfacts.model.api.CatService
import com.example.catfacts.catfact.CatFactState
import com.example.catfacts.catfact.CatFactViewModel
import com.example.catfacts.catfact.GetCatFactUseCase
import com.example.catfacts.model.CatFactRepository
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class DependencyInjection {

    var catFactViewModel: CatFactViewModel

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

        val getCatFactUseCase = GetCatFactUseCase(catFactRepository)

        catFactViewModel = CatFactViewModel(CatFactState(activity = false), getCatFactUseCase)
    }
}