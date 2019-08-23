package com.example.catfacts.catfact

import com.ww.roxie.BaseState

data class CatFactState(
    val activity: Boolean = false,
    val fact: String = "",
    val displayError: Boolean = false
) : BaseState