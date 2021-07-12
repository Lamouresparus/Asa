package com.android.asa.ui.reading_progress

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.asa.databinding.FragmentReadingProgressBinding
import com.android.asa.extensions.makeInvisible
import com.android.asa.extensions.makeVisible
import com.android.asa.extensions.showToast
import com.android.asa.ui.reading_timetable.ReadTimeTableCoursesAdapter
import com.android.asa.utils.Result
import com.asa.domain.model.CourseTotalReadingHoursDomain
import com.asa.domain.model.DailyTotalReadingHoursDomain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReadingProgressFragment : Fragment() {

    private val viewModel by viewModels<ReadingProgressViewModel>()
    private lateinit var dailyReadingChartAdapter: DailyReadingChartAdapter
    private lateinit var readTimeTableCoursesAdapter: ReadTimeTableCoursesAdapter
    private var readingCourses = mutableListOf<CourseTotalReadingHoursDomain>()
    private var dailyTotalReadingHours = mutableListOf<DailyTotalReadingHoursDomain>()
    private lateinit var binding: FragmentReadingProgressBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReadingProgressBinding.inflate(layoutInflater)
        setUpRv()
        return binding.root
    }

    private fun setUpRv() {
        dailyReadingChartAdapter = DailyReadingChartAdapter(dailyTotalReadingHours)
        binding.readingChartRecyclerView.apply {
            adapter = dailyReadingChartAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        }

        readTimeTableCoursesAdapter = ReadTimeTableCoursesAdapter(readingCourses)
        binding.readingCoursesRecyclerView.apply {
            adapter = readTimeTableCoursesAdapter
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getViewContent()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.viewContent.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    binding.readingChartProgressBar.makeVisible()
                    binding.readingTimeProgressBar.makeVisible()
                    binding.readingCoursesProgressBar.makeVisible()
                }
                is Result.Success -> {
                    readingCourses.clear()
                    dailyTotalReadingHours.clear()
                    result.data.let {
                        it?.totalReadingHours?.let { courseReadingHours -> readingCourses.addAll(courseReadingHours) }
                        it?.dailyReadingHours?.let { dailyReadHours -> dailyTotalReadingHours.addAll(dailyReadHours) }
                        binding.averageReadingTime.text = it?.averageReadingTime?.let { it1 -> setTime(it1) }
                        binding.overallReadingTime.text = it?.overallReadingHours?.let { it1 -> setTime(it1) }

                    }
                    readTimeTableCoursesAdapter.notifyDataSetChanged()
                    dailyReadingChartAdapter.notifyDataSetChanged()
                    binding.readingChartProgressBar.makeInvisible()
                    binding.readingTimeProgressBar.makeInvisible()
                    binding.readingCoursesProgressBar.makeInvisible()
                }
                is Result.Error -> {
                    binding.readingChartProgressBar.makeInvisible()
                    binding.readingTimeProgressBar.makeInvisible()
                    binding.readingCoursesProgressBar.makeInvisible()

                    showToast(result.errorMessage)
                    Log.d("reading", "error ${result.errorMessage}")
                }
            }
        })
    }

    fun setTime(time: Double): String {
        val hours = time.toInt()
        val mins = ((time - hours) * 60).toInt()
        return hours.toString().plus(" hrs").plus("\n$mins").plus(" mins")
    }
}