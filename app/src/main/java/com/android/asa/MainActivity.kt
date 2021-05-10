package com.android.asa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.asa.databinding.ActivityMainBinding
import com.android.asa.ui.add_course.AddCourseFragment
import com.android.asa.ui.assignments_and_tests.AssignmentsFragment
import com.android.asa.ui.cgpa_projector.CgpaProjectorFragment
import com.android.asa.ui.home.HomeFragment
import com.android.asa.ui.reading_timetable.ReadingTimetableFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            openFragment(fragment)
        }
        setupBottomNav()

        setContentView(binding.root)


    }

    private fun setupBottomNav() {

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.navigation_home -> HomeFragment()
                R.id.navigation_reading_progress -> AddCourseFragment()
                R.id.navigation_reading_timetable -> ReadingTimetableFragment()
                R.id.navigation_cgpa_projector -> CgpaProjectorFragment()
                R.id.navigation_assignments_and_tests -> AssignmentsFragment()
                else -> return@OnNavigationItemSelectedListener false

            }
            openFragment(fragment)
            return@OnNavigationItemSelectedListener true

        }

        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}