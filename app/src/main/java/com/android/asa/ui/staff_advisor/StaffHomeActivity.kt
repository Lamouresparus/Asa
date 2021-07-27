package com.android.asa.ui.staff_advisor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.asa.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StaffHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_home)
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, StaffHomeActivity::class.java)
        }
    }
}