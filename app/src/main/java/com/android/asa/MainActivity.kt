package com.android.asa

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.android.asa.databinding.ActivityMainBinding
import com.android.asa.extensions.makeGone
import com.android.asa.extensions.makeVisible
import com.android.asa.ui.profile.ProfileViewModel
import com.android.asa.utils.Constants.INTENT_SHOW_TIMER_FRAGMENT
import com.asa.data.sharedPreference.SharedPreferenceReader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<ProfileViewModel>()

    @Inject
    lateinit var prefReader: SharedPreferenceReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()

        val fragmentsToHide = listOf(
            R.id.profileFragment,
            R.id.editProfileFragment,
            R.id.readingTimerFragment,
            R.id.readingCompleteFragment
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                in fragmentsToHide -> binding.buttomNavigation.makeGone()
                else -> binding.buttomNavigation.makeVisible()
            }
        }

        navigateToReadingTimerIfNecessary()
    }

    private fun navigateToReadingTimerIfNecessary() {

        if (intent.extras != null) {
            val bundle = intent.extras
            val showTimer = bundle?.getBoolean(INTENT_SHOW_TIMER_FRAGMENT) ?: false

            val courseBundle = Bundle().apply {
                putSerializable("userCourse", viewModel.getUserCourse())
            }
            if (showTimer) {
                viewModel.showTimerCountDown = true
                navController.navigate(
                    R.id.readingTimerFragment,
                    courseBundle
                )
            }
        }
    }

    private fun setupViews() {
        navController = Navigation.findNavController(this, R.id.nav_container)

        with(prefReader.getSemesterInformation()) {
            if (this == null) throw IllegalStateException("no course information found")

            val navGraph = if (hasSemesterBegun && noOfCoursesOffered > 0) {
                binding.buttomNavigation.inflateMenu(R.menu.home_navigation_menu)
                navController.navInflater.inflate(R.navigation.home_nav_graph).apply {
                    startDestination = R.id.homeFragment
                }
            } else {
                if (!hasSemesterBegun) {
                    binding.buttomNavigation.inflateMenu(R.menu.home_navigation_menu_2)
                    navController.navInflater.inflate(R.navigation.home_nav_graph).apply {
                        startDestination = R.id.beginSemesterFragment
                    }
                } else {
                    binding.buttomNavigation.inflateMenu(R.menu.home_navigation_menu_3)
                    navController.navInflater.inflate(R.navigation.home_nav_graph).apply {
                        startDestination = R.id.addSemesterCoursesFragment
                    }
                }
            }
            navController.graph = navGraph
        }

        binding.buttomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}