package com.android.asa.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.asa.MainActivity
import com.android.asa.R
import com.android.asa.ui.auth.AuthenticationActivity
import com.android.asa.ui.staff_advisor.StaffHomeActivity
import com.asa.data.sharedPreference.SharedPreferenceReader
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var fireBaseAuth: FirebaseAuth

    @Inject
    lateinit var prefReader: SharedPreferenceReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launchWhenCreated {
            delay(1500)

            if (fireBaseAuth.currentUser != null) {

                if(prefReader.getUserData()?.userType == 0){
                    navigateToMainActivity()

                }
                else{
                    navigateToStaffHomeActivity()

                }
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, AuthenticationActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToStaffHomeActivity() {
        val intent = Intent(this, StaffHomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}