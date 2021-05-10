package com.android.asa.ui.add_course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.databinding.FragmentAddCourseBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.asa.domain.model.LectureDayDomain
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddCourseFragment : BaseFragment(), LectureDayListener {
    private lateinit var binding: FragmentAddCourseBinding

    private var lectureDays = mutableListOf<LectureDayDomain>()
    private var lectureVenueDetailsAdapter = LectureVenueDetailsAdapter(lectureDays, this@AddCourseFragment)

//    private val viewModel by activityViewModels<AddCoursesViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddCourseBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    private fun setUpViews() {
        binding.lectureDayRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = LectureDaysAdapter(this@AddCourseFragment)
        }

        binding.courseDetailsRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lectureVenueDetailsAdapter
        }

        binding.saveButton.setOnClickListener {

            if (lectureDays.isEmpty()) {
                showToast("pls set up a lecture days")
                return@setOnClickListener
            }

            showToast(lectureDays.toString())
            println(lectureDays.toString())
        }
    }

    override fun isChecked(day: LectureDayDomain) {
        lectureDays.add(day)
        lectureVenueDetailsAdapter.notifyItemInserted(lectureDays.size - 1)
    }

    override fun isUnchecked(dayOfWeek: String) {
        val dayToRemove = lectureDays.find { it.dayOfWeek == dayOfWeek }
        val position = lectureDays.indexOf(dayToRemove)
        lectureDays.removeAt(position)

        lectureVenueDetailsAdapter.notifyItemRemoved(position)
    }


//    private fun getAddCourseParams() {
//
//        val course = CourseDomain(
//                title = binding.courseTitleEt.text.trim().toString(),
//                courseCode = binding.courseCodeEt.text.trim().toString(),
//                creditUnit = binding.creditUnitEt.text.trim().toString().toInt(),
//                description = binding.courseDescriptionEt.text.trim().toString(),
//                lecturer = binding.lecturerNameEt.text.trim().toString(),
//                lectureDays =
//
//        )
//        return AddCourseUseCase.Params()
//    }

}