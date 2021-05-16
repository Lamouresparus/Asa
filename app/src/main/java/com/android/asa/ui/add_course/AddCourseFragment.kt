package com.android.asa.ui.add_course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.databinding.FragmentAddCourseBinding
import com.android.asa.extensions.showToast
import com.android.asa.ui.common.BaseFragment
import com.android.asa.utils.Result
import com.asa.domain.AddCourseUseCase
import com.asa.domain.model.CourseDomain
import com.asa.domain.model.LectureDayDomain
import com.classic.chatapp.utils.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AddCourseFragment : BaseFragment(), LectureDayListener {
    private lateinit var binding: FragmentAddCourseBinding

    private var lectureDays = mutableListOf<LectureDayDomain>()
    private var lectureVenueDetailsAdapter = LectureVenueDetailsAdapter(lectureDays, this@AddCourseFragment)

    private val viewModel by activityViewModels<AddCoursesViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddCourseBinding.inflate(layoutInflater)
        setUpViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
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

            if (binding.courseTitleEt.text.trim().isEmpty()) {
                showToast("course title cannot be empty")
                return@setOnClickListener
            }

            if (binding.courseCodeEt.text.trim().isEmpty()) {
                showToast("course code cannot be empty")
                return@setOnClickListener
            }
            if (binding.courseDescriptionEt.text.trim().isEmpty()) {
                showToast("course description cannot be empty")
                return@setOnClickListener
            }
            if (binding.creditUnitEt.text.trim().isEmpty()) {
                showToast("course credit unit cannot be empty")
                return@setOnClickListener
            }
            if (binding.lecturerNameEt.text.trim().isEmpty()) {
                showToast("lecturer name cannot be empty")
                return@setOnClickListener
            }


            if (lectureDays.isEmpty()) {
                showToast("please set up lecture days")
                return@setOnClickListener
            }
            for (lectureDay in lectureDays) {
                val day = lectureDay.dayOfWeek
                if (lectureDay.venue.isEmpty()) {
                    showToast("No venue inputted for $day")
                    return@setOnClickListener

                }
                if (lectureDay.startTime.isEmpty() || lectureDay.endTime.isEmpty()) {
                    showToast("Lecture time for $day not set")
                    return@setOnClickListener

                }
                if (lectureDay.startTime.toInt() >= lectureDay.endTime.toInt()) {
                    showToast("Invalid lecture time for $day")
                    return@setOnClickListener

                }
            }

            viewModel.saveCourses(getAddCourseParams())
        }
    }

    private fun observeData() {

        viewModel.addCourse.observe(viewLifecycleOwner, EventObserver { result ->
            when (result) {
                is Result.Loading -> {
                    showProgressDialog("Adding ${binding.courseTitleEt.text.trim()}...")
                }

                is Result.Success -> {
                    lectureDays.clear()
                    lectureVenueDetailsAdapter.notifyDataSetChanged()
                    findNavController().navigate(AddCourseFragmentDirections.actionAddCourseFragmentToAddAllCoursesFragment2())

                    hideProgressDialog()
                }

                is Result.Error -> {
                    showToast(result.errorMessage)
                    hideProgressDialog()

                }
            }

        })

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


    private fun getAddCourseParams(): AddCourseUseCase.Params {
        val course = CourseDomain(
                title = binding.courseTitleEt.text.trim().toString(),
                courseCode = binding.courseCodeEt.text.trim().toString(),
                creditUnit = binding.creditUnitEt.text.trim().toString().toInt(),
                description = binding.courseDescriptionEt.text.trim().toString(),
                lecturer = binding.lecturerNameEt.text.trim().toString(),
                lectureDays = lectureDays

        )
        return AddCourseUseCase.Params(course)
    }

}