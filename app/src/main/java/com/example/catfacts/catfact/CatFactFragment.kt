package com.example.catfacts.catfact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catfacts.CatFact
import com.example.catfacts.CatService
import com.example.catfacts.R
import com.example.catfacts.Response
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.cat_fact_fragment.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random

class CatFactFragment : Fragment() {

    private lateinit var retrofit: Retrofit
    private lateinit var catService: CatService

    companion object {
        fun newInstance() = CatFactFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.cat_fact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiBaseUrl = "https://cat-fact.herokuapp.com"
        val moshi = Moshi.Builder().build()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()

        catService = retrofit.create(CatService::class.java)
        getFactButton.setOnClickListener {
            val call = catService.getFacts()
            progressBar.visibility = View.VISIBLE
            getFactButton.isEnabled = false
            call.enqueue(object : Callback<Response<List<CatFact>>> {
                override fun onFailure(
                    call: Call<Response<List<CatFact>>>,
                    t: Throwable
                ) {
                    Toast
                        .makeText(
                            requireContext(),
                            "Something went wrong!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    progressBar.visibility = View.GONE
                    getFactButton.isEnabled = true
                }

                override fun onResponse(
                    call: Call<Response<List<CatFact>>>,
                    response: retrofit2.Response<Response<List<CatFact>>>
                ) {
                    val randomInt = Random.nextInt(0, response.body()!!.all.size)
                    catFactView.text = response.body()!!.all.get(randomInt).text
                    progressBar.visibility = View.GONE
                    getFactButton.isEnabled = true
                }
            })
        }
    }
}