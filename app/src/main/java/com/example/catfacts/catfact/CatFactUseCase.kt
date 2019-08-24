package com.example.catfacts.catfact

import com.example.catfacts.model.CatFactRepository
import io.reactivex.Single

interface CatFactUseCase {
    fun getFact(): Single<String>
}

class CatFactUseCaseImpl(private val catFactRepository: CatFactRepository) : CatFactUseCase {
    override fun getFact(): Single<String> = catFactRepository.getCatFact()
}