package com.example.catfacts.catfact

import com.ww.roxie.BaseViewModel
import com.ww.roxie.Reducer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers

class CatFactViewModel(
    initialState: CatFactState?,
    private val catFactUseCase: CatFactUseCase
) : BaseViewModel<CatFactAction, CatFactState>() {

    override val initialState = initialState ?: CatFactState(activity = false)

    private val reducer: Reducer<CatFactState, CatFactChange> = { state, change ->
        when (change) {
            is CatFactChange.Loading -> state.copy(
                activity = true,
                displayError = false
            )
            is CatFactChange.Fact -> state.copy(
                activity = false,
                fact = change.fact,
                displayError = false
            )
            is CatFactChange.Error -> state.copy(
                activity = false,
                displayError = true
            )
        }
    }

    init {
        bindActions()
    }

    private fun bindActions() {
        val getFactChange = actions
            .ofType<CatFactAction.GetFactButtonClicked>()
            .switchMap {
                catFactUseCase.getFact()
                    .subscribeOn(Schedulers.io())
                    .toObservable()
                    .map<CatFactChange> { CatFactChange.Fact(it) }
                    .onErrorReturn { CatFactChange.Error(it) }
                    .startWith(CatFactChange.Loading)
            }

        disposables += getFactChange
            .scan(initialState, reducer)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)
    }
}