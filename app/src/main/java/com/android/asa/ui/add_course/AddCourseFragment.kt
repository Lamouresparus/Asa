package com.android.asa.ui.add_course

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.databinding.FragmentAddCourseBinding


class AddCourseFragment : Fragment(), LectureDayListener {
    private lateinit var binding: FragmentAddCourseBinding
    private var lectureDays: ArrayList<String> = ArrayList()
    private var lectureVenueDetailsAdapter = LectureVenueDetailsAdapter(lectureDays, this@AddCourseFragment)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddCourseBinding.inflate(layoutInflater)
        setUpRv()
        setUpClickListeners()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUpClickListeners() {
        binding.saveButton.setOnClickListener {
            findNavController().navigate(AddCourseFragmentDirections.actionAddCourseFragmentToAddAllCoursesFragment2())
        }
    }

    private fun setUpRv() {
        binding.lectureDayRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = LectureDaysAdapter(this@AddCourseFragment)
        }

        binding.courseDetailsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lectureVenueDetailsAdapter
        }

    }

    override fun isChecked(day: String) {
        lectureDays.add(day)
        lectureVenueDetailsAdapter.notifyDataSetChanged()
    }

    override fun isUnchecked(day: String) {
        lectureDays.remove(day)
        lectureVenueDetailsAdapter.notifyDataSetChanged()


    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun setLectureTime(tv: TextView, text: String) {
        val mcurrentTime: Calendar = Calendar.getInstance()
        val hour: Int = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute: Int = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker = TimePickerDialog(context, { _, selectedHour, selectedMinute -> "$selectedHour:$selectedMinute".also { tv.text = it } }, hour, minute, true) //Yes 24 hour time

        mTimePicker.setTitle(text)
        mTimePicker.show()

    }

}