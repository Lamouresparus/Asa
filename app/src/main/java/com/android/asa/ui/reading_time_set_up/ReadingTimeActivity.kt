package com.android.asa.ui.reading_time_set_up


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.asa.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingTimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading_time)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}