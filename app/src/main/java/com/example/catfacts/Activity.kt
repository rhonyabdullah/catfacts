package com.example.catfacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catfacts.catfact.CatFactFragment

class Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CatFactFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }
}
