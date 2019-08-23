package com.example.catfacts.catfact

sealed class CatFactChange {
    object Loading : CatFactChange()
    data class Fact(val fact: String) : CatFactChange()
    data class Error(val throwable: Throwable?) : CatFactChange()
}