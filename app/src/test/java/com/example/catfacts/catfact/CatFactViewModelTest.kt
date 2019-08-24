package com.example.catfacts.catfact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.catfacts.RxTestSchedulerRule
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CatFactViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxTestSchedulerRule()

    private lateinit var testSubject: CatFactViewModel

    private val initialState = CatFactState(activity = false)

    private val loadingState = CatFactState(activity = true)

    private val catFactUseCase = mock<CatFactUseCase>()

    private val observer = mock<Observer<CatFactState>>()

    @Before
    fun setUp() {
        testSubject = CatFactViewModel(initialState, catFactUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given fact successfully loaded, when action GetCatFact is received, then State contains fact`() {
        // GIVEN
        val fact = "Programmers love cats."
        val successState = CatFactState(activity = false, fact = fact)

        whenever(catFactUseCase.getFact()).thenReturn(Single.just(fact))

        // WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(initialState)
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(successState)
        }

        verifyNoMoreInteractions(observer)
    }

    @Test
    fun `Given fact failed to load, when action GetCatFact received, then State contains error`() {
        // GIVEN
        whenever(catFactUseCase.getFact()).thenReturn(Single.error(RuntimeException()))
        val errorState = CatFactState(displayError = true)

        // WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testSchedulerRule.triggerActions()

        // THEN
        inOrder(observer) {
            verify(observer).onChanged(initialState)
            verify(observer).onChanged(loadingState)
            verify(observer).onChanged(errorState)
        }

        verifyNoMoreInteractions(observer)
    }
}