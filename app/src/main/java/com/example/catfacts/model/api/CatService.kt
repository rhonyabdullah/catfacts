package com.example.catfacts.model.api

import com.example.catfacts.model.Response
import com.example.catfacts.model.CatFact
import io.reactivex.Single
import retrofit2.http.GET

interface CatService {

    @GET("facts")
    fun getFacts(): Single<Response<List<CatFact>>>
}