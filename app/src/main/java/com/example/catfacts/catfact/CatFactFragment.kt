package com.example.catfacts.catfact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.catfacts.Application
import com.example.catfacts.R
import kotlinx.android.synthetic.main.cat_fact_fragment.*

class CatFactFragment : Fragment() {

    companion object {
        fun newInstance() = CatFactFragment()
    }

    private lateinit var viewModel: CatFactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cat_fact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (requireActivity().application as Application).di.catFactViewModel

        viewModel.observableState.observe(this, Observer { state ->
            state?.let { renderState(state) }
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
            errorView.isVisible = displayError
        }
    }
}