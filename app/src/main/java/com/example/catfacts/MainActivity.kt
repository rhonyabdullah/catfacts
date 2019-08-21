package com.example.catfacts

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var catService: CatService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        val getFactButton = findViewById<Button>(R.id.getFactButton)
        val loadingGroup = findViewById<ProgressBar>(R.id.progressBar)
        val catFactView = findViewById<TextView>(R.id.catFactView)
        getFactButton.setOnClickListener {
            val call = catService.getFacts()
            loadingGroup.visibility = View.VISIBLE
            call.enqueue(object : Callback<com.example.catfacts.Response<List<CatFact>>> {
                override fun onFailure(
                    call: Call<com.example.catfacts.Response<List<CatFact>>>,
                    t: Throwable
                ) {
                    Toast
                        .makeText(
                            this@MainActivity,
                            "Something went wrong!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    loadingGroup.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<com.example.catfacts.Response<List<CatFact>>>,
                    response: Response<com.example.catfacts.Response<List<CatFact>>>
                ) {
                    val randomInt = Random.nextInt(0, response.body()!!.all.size)
                    catFactView.text = response.body()!!.all.get(randomInt).text
                    loadingGroup.visibility = View.GONE
                }
            })
        }
    }
}
