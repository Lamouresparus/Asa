package com.android.asa.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.asa.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_authentication)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


}