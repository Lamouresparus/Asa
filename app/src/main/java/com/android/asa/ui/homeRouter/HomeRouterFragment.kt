package com.android.asa.ui.homeRouter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.asa.R
import com.asa.data.sharedPreference.SharedPreferenceReader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeRouterFragment : Fragment() {

    @Inject
    lateinit var prefReader: SharedPreferenceReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navController = findNavController()
        with(prefReader.getSemesterInformation()) {
            if (hasSemesterBegun && noOfCoursesOffered > 0) {
                // navigate to home screen
                navController.navigate(R.id.homeFragment)
            } else {
                if (!hasSemesterBegun) navController.navigate(R.id.beginSemesterFragment)
                else navController.navigate(R.id.addSemesterCoursesFragment)
            }
        }
    }
}