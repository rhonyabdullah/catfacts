package com.example.catfacts.model

import com.example.catfacts.model.api.CatService
import io.reactivex.Single
import kotlin.random.Random

class CatFactRepository(private val catFactService: CatService) {

    fun getCatFact(): Single<String> {
        return catFactService.getFacts()
            .map { response ->
                val randomInt = Random.nextInt(0, response.all.size)
                response.all[randomInt].text
            }
    }
}