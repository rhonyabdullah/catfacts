package com.example.catfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var catService: CatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiBaseUrl = "https://cat-fact.herokuapp.com"

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .client(okHttpClient)
            .build()

        catService = retrofit.create(CatService::class.java)
    }
}
