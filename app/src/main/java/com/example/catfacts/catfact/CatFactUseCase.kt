package com.example.catfacts.catfact

import com.example.catfacts.model.CatFactRepository
import io.reactivex.Single

class GetCatFactUseCase(private val catFactRepository: CatFactRepository) {
    fun getFact(): Single<String> = catFactRepository.getCatFact()
}