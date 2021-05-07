package com.android.asa

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.asa.databinding.ActivityMainBinding
import com.android.asa.ui.assignments_and_tests.AssignmentsFragment
import com.android.asa.ui.cgpa_projector.CgpaProjectorFragment
import com.android.asa.ui.home.HomeFragment
import com.android.asa.ui.reading_progress.ReadingProgressFragment
import com.android.asa.ui.reading_timetable.ReadingTimetableFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigation: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            openFragment(fragment)
        }
        setupBottoNav()

        setContentView(binding.root)


    }

    private fun setupBottoNav() {

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Log.d("nav clicked", "home")
                    val homeFragment = HomeFragment()
                    openFragment(homeFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_reading_progress -> {
                    Log.d("nav clicked", "redaing progress")

                    val readingProgressFragment = ReadingProgressFragment()
                    openFragment(readingProgressFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_reading_timetable -> {
                    Log.d("nav clicked", "reading timetable")

                    val readingTimetableFragment = ReadingTimetableFragment()
                    openFragment(readingTimetableFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_cgpa_projector -> {

                    val cgpaProjectorFragment = CgpaProjectorFragment()
                    openFragment(cgpaProjectorFragment)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_assignments_and_tests -> {

                    val assignmentsFragment = AssignmentsFragment()
                    openFragment(assignmentsFragment)

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}