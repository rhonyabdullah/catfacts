package com.example.catfacts.catfact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.catfacts.R
import com.example.catfacts.di
import com.ww.roxie.BaseViewModel
import kotlinx.android.synthetic.main.cat_fact_fragment.*

class CatFactFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactFragment()
    }

    private lateinit var viewModel: BaseViewModel<CatFactAction, CatFactState>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cat_fact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = this.context!!.di.catFactViewModel

        viewModel.observableState.observe(this, Observer { state ->
            state?.let {
                renderState(it)
            }
        })

        getFactButton.setOnClickListener {
            viewModel.dispatch(CatFactAction.GetFactButtonClicked)
        }
    }

    private fun renderState(state: CatFactState) {
        with(state) {
            if (fact.isNotEmpty()) {
                catFactView.text = fact
            }
            progressBar.isVisible = activity
            getFactButton.isEnabled = !activity
            errorView.isVisible = displayError
        }
    }
}