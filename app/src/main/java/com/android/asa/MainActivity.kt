package com.android.asa

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.android.asa.databinding.ActivityMainBinding
import com.android.asa.extensions.makeGone
import com.android.asa.extensions.makeInvisible
import com.android.asa.extensions.makeVisible
import com.android.asa.ui.profile.UserCourses
import com.android.asa.utils.Constants.INTENT_SHOW_TIMER_FRAGMENT
import com.android.asa.utils.Constants.INTENT_USER_COURSE_DATA_BUNDLE
import com.android.asa.utils.Constants.SHOW_TIMER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "INTENT CALLED")

        if (intent?.getBooleanExtra(INTENT_SHOW_TIMER_FRAGMENT, false) == true) {
            val courseData = intent.getParcelableExtra<UserCourses>(INTENT_USER_COURSE_DATA_BUNDLE)
            Log.d(TAG, "This is the Users Courses $courseData ")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()


        if (intent.extras != null) {
            var bundle = intent.extras
            val showTimer = bundle?.getBoolean(INTENT_SHOW_TIMER_FRAGMENT)
            val userCourse = bundle?.getBundle(INTENT_USER_COURSE_DATA_BUNDLE)
            val courseBundle = Bundle().apply {
                putParcelable("userCourses", userCourse)
                putBoolean(SHOW_TIMER,true)
            }
            if (showTimer!!) {
                navController.navigate(
                        R.id.action_addSemesterCoursesFragment_to_readingTimerFragment,
                        courseBundle
                )
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.profileFragment -> binding.buttomNavigation.makeGone()
                R.id.editProfileFragment -> binding.buttomNavigation.makeGone()
                R.id.readingTimerFragment -> binding.buttomNavigation.makeGone()
                R.id.readingCompleteFragment -> binding.buttomNavigation.makeGone()

                else -> {
                    binding.buttomNavigation.makeVisible()
                }
            }
        }
    }

    private fun setupViews() {
        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.nav_container)
        //Setting the navigation controller to Bottom Nav
        binding.buttomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    companion object {
        private const val TAG = "CountUpTimerService"
    }
}