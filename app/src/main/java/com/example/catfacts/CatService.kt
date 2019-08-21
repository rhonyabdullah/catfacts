package com.example.catfacts

import retrofit2.Call
import retrofit2.http.GET

interface CatService {

    @GET("facts")
    fun getFacts(): Call<List<CatFact>>
}