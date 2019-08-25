package com.example.catfacts

import com.example.catfacts.catfact.CatFactAction
import com.example.catfacts.catfact.CatFactState
import com.ww.roxie.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class AndroidTestViewModel : BaseViewModel<CatFactAction, CatFactState>() {

    val testAction = TestObserver<CatFactAction>()
    val testState = PublishSubject.create<CatFactState>()

    override val initialState = CatFactState()

    init {
        actions.subscribe(testAction)
        disposables += testState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }
}
