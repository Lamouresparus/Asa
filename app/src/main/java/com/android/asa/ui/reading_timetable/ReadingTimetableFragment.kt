package com.android.asa.ui.reading_timetable

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.databinding.FragmentReadingTimetableBinding
import com.android.asa.extensions.makeInvisible
import com.android.asa.extensions.makeVisible
import com.android.asa.extensions.showToast
import com.android.asa.utils.Result
import com.asa.domain.model.CourseTotalReadingHoursDomain
import com.asa.domain.model.ReadingTimetableDomain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingTimetableFragment : Fragment() {

    private val viewModel by viewModels<ReadingTimetableViewModel>()

    private lateinit var readingTimetableAdapter: ReadingTimetableAdapter
    private lateinit var readingTimetableCoursesAdapter: ReadTimeTableCoursesAdapter
    private var readingCourses = mutableListOf<CourseTotalReadingHoursDomain>()
    private var readingTimetable = mutableListOf<ReadingTimetableDomain>()
    private lateinit var binding: FragmentReadingTimetableBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.readingTimetable.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.readingCoursesProgressBar.makeVisible()
                    binding.readingTimetableProgressBar.makeVisible()
                }

                is Result.Success -> {
                    readingTimetable.clear()
                    readingCourses.clear()
                    result.data?.toList()?.let {
                        readingTimetable.addAll(it)
                        readingCourses.addAll(viewModel.getTotalReadTime(it))
                    }
                    readingTimetableAdapter.notifyDataSetChanged()
                    readingTimetableCoursesAdapter.notifyDataSetChanged()
                    binding.readingTimetableProgressBar.makeInvisible()
                    binding.readingCoursesProgressBar.makeInvisible()


                    Log.d("reading", "List of sorted day is $readingTimetable")
                }

                is Result.Error -> {
                    binding.readingTimetableProgressBar.makeInvisible()
                    binding.readingCoursesProgressBar.makeInvisible()

                    showToast(result.errorMessage)
                    Log.d("reading", "error ${result.errorMessage}")
                }
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getReadingTimeTable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingTimetableBinding.inflate(layoutInflater)
        setUpRv()
        return binding.root
    }

    private fun setUpRv() {
        readingTimetableAdapter = ReadingTimetableAdapter(readingTimetable)
        binding.readingTimetableRecyclerView.apply {
            adapter = readingTimetableAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        readingTimetableCoursesAdapter = ReadTimeTableCoursesAdapter(readingCourses)
        binding.coursesRecyclerView.apply {
            adapter = readingTimetableCoursesAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }
    }
}